package com.github.almasud.rickandmorty.character.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.almasud.rickandmorty.character.data.mappers.toDomain
import com.github.almasud.rickandmorty.character.data.sources.paging.CharacterRemoteMediator
import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.character.domain.repositories.CharacterRepo
import com.github.almasud.rickandmorty.core.data.local.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRepoImpl @Inject constructor(
    private val database: AppDatabase,
    private val remoteMediator: CharacterRemoteMediator
) : CharacterRepo {

    override fun getCharacters(): Flow<PagingData<Character>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = NETWORK_PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.characterDao().pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { entity -> entity.toDomain() }
        }

    override suspend fun getCharacter(id: Int): Character? =
        database.characterDao().getCharacterById(id)?.toDomain()

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
