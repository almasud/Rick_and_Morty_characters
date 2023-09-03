/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 4/9/2023
 */

package com.github.almasud.rick_and_morty.data.repo

import com.github.almasud.rick_and_morty.data.api.CharacterApiService
import com.github.almasud.rick_and_morty.data.api.resposne.NetworkResult
import com.github.almasud.rick_and_morty.data.api.resposne.model.CharactersResponse
import com.github.almasud.rick_and_morty.domain.repo.CharacterRepo
import javax.inject.Inject

class CharacterRepoImpl @Inject constructor(
    private val apiService: CharacterApiService
) : CharacterRepo {
    override suspend fun getCharacters(page: Int): NetworkResult<CharactersResponse> =
        NetworkResult.handleRestApiResponse {
            apiService.getCharacters(page = page)
        }
}