package com.github.almasud.rickandmorty.character.data.sources.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.almasud.rickandmorty.character.data.local.entities.CharacterEntity
import com.github.almasud.rickandmorty.character.data.local.entities.CharacterRemoteKeyEntity
import com.github.almasud.rickandmorty.character.data.mappers.toCharacterEntity
import com.github.almasud.rickandmorty.character.data.sources.CharacterDataSource
import com.github.almasud.rickandmorty.core.data.local.db.AppDatabase
import com.github.almasud.rickandmorty.core.data.remote.models.RemoteResult
import com.github.almasud.rickandmorty.core.domain.models.DataErrorException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val characterDataSource: CharacterDataSource,
    private val database: AppDatabase
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        Log.d(TAG, "initialize: is called")
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKey?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                val nextKey = remoteKey?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKey != null
                )
                nextKey
            }
        }

        return when (val apiResponse = characterDataSource.getCharacters(page)) {
            is RemoteResult.Success -> {
                val characters = apiResponse.data.resultsDto
                val isEndOfPaginationReached = characters.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.remoteKeyDao().clearAllRemoteKeys()
                        database.characterDao().clearAll()
                    }

                    val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (isEndOfPaginationReached) null else page + 1

                    val remoteKeys = characters.map {
                        CharacterRemoteKeyEntity(
                            characterId = it.id,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                    database.remoteKeyDao().insertAllRemoteKeys(remoteKeys)
                    database.characterDao().insertAll(
                        characters.map { dto -> dto.toCharacterEntity() }
                    )
                }

                MediatorResult.Success(endOfPaginationReached = isEndOfPaginationReached)
            }

            is RemoteResult.Error -> {
                MediatorResult.Error(DataErrorException(apiResponse.error))
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>) =
        state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                database.remoteKeyDao().getRemoteKeyById(character.id)
            }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>) =
        state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                database.remoteKeyDao().getRemoteKeyById(character.id)
            }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): CharacterRemoteKeyEntity? =
        state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeyDao().getRemoteKeyById(id)
            }
        }

    companion object {
        private val TAG = CharacterRemoteMediator::class.simpleName
        const val STARTING_PAGE_INDEX = 1
    }
}
