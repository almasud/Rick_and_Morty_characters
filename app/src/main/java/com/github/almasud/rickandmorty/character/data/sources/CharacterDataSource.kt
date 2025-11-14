package com.github.almasud.rickandmorty.character.data.sources

import com.github.almasud.rickandmorty.character.data.remote.dto.CharacterResponseDto
import com.github.almasud.rickandmorty.core.data.remote.models.RemoteResult
import com.github.almasud.rickandmorty.core.domain.models.DataError

interface CharacterDataSource {
    suspend fun getCharacters(page: Int): RemoteResult<CharacterResponseDto, DataError.Remote>
}