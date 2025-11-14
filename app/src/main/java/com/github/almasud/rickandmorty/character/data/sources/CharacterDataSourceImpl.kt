package com.github.almasud.rickandmorty.character.data.sources

import com.github.almasud.rickandmorty.character.data.remote.dto.CharacterResponseDto
import com.github.almasud.rickandmorty.character.data.remote.api.ApiService
import com.github.almasud.rickandmorty.character.data.remote.api.handleRestApiCall
import com.github.almasud.rickandmorty.core.data.remote.models.RemoteResult
import com.github.almasud.rickandmorty.core.domain.models.DataError
import javax.inject.Inject

class CharacterDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : CharacterDataSource {
    override suspend fun getCharacters(page: Int): RemoteResult<CharacterResponseDto, DataError.Remote> {
        return handleRestApiCall {
            apiService.getCharacters(page)
        }
    }

}