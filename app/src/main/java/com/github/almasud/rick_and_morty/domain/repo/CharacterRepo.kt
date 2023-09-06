/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 3/9/2023
 */

package com.github.almasud.rick_and_morty.domain.repo

import com.github.almasud.rick_and_morty.data.api.resposne.NetworkResult
import com.github.almasud.rick_and_morty.data.api.resposne.model.CharactersResponse
import com.github.almasud.rick_and_morty.domain.model.Character

interface CharacterRepo {
    suspend fun getCharacters(page: Int): NetworkResult<CharactersResponse>

    suspend fun getCharacterById(id: Long): Character
}