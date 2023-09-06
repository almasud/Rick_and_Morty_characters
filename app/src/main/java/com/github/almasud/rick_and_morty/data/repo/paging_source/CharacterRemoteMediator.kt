/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 6/9/2023
 */

package com.github.almasud.rick_and_morty.data.repo.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.almasud.rick_and_morty.data.api.resposne.NetworkResult
import com.github.almasud.rick_and_morty.data.db.AppDatabase
import com.github.almasud.rick_and_morty.data.db.RemoteKeys
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.domain.repo.CharacterRepo
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

private const val CHARACTER_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterRepo: CharacterRepo,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Character>() {

    override suspend fun initialize(): InitializeAction {
        Timber.d("initialize: is called")

        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        Timber.d("load: is called")

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: CHARACTER_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val apiResponse = characterRepo.getCharacters(page)
            Timber.d("load: apiResponse: $apiResponse")

            when (apiResponse) {
                is NetworkResult.Error -> MediatorResult.Error(Exception(apiResponse.message))
                is NetworkResult.Exception -> MediatorResult.Error(apiResponse.e)
                is NetworkResult.Success -> {
                    val characters = apiResponse.data.characters
                    val endOfPaginationReached = characters.isEmpty()
                    appDatabase.withTransaction {
                        // clear all tables in the database
                        if (loadType == LoadType.REFRESH) {
                            appDatabase.remoteKeysDao().clearRemoteKeys()
                            appDatabase.characterDao().clearCharacters()
                        }
                        val prevKey = if (page == CHARACTER_STARTING_PAGE_INDEX) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val keys = characters.map {
                            RemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
                        }
                        appDatabase.remoteKeysDao().insertAll(keys)
                        appDatabase.characterDao().insertAll(characters)
                    }
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
            }

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                appDatabase.remoteKeysDao().remoteKeysByCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                appDatabase.remoteKeysDao().remoteKeysByCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): RemoteKeys? {
        Timber.d("getRemoteKeyClosestToCurrentPosition: is called")

        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                appDatabase.remoteKeysDao().remoteKeysByCharacterId(characterId)
            }
        }
    }
}