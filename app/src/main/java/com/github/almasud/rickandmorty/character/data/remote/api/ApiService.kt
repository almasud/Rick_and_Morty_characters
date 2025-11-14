package com.github.almasud.rickandmorty.character.data.remote.api

import com.github.almasud.rickandmorty.character.data.remote.dto.CharacterResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/character")
    suspend fun getCharacters(
        @Query(
            "page"
        ) pageNumber: Int
    ): Response<CharacterResponseDto>
}