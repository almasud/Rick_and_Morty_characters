/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 3/9/2023
 */

package com.github.almasud.rick_and_morty.data.api

import com.github.almasud.rick_and_morty.data.api.resposne.model.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : Response<CharactersResponse>
}