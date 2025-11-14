package com.github.almasud.rickandmorty.character.domain.repositories

import androidx.paging.PagingData
import com.github.almasud.rickandmorty.character.domain.models.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepo {
    fun getCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacter(id: Int): Character?
}
