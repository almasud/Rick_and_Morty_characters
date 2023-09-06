/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 7/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.domain.repo.CharacterRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileVM @Inject constructor(
    private val characterRepo: CharacterRepo
) : ViewModel() {
    val character: MutableState<Character?> = mutableStateOf(null)

    fun getCharacterById(id: Long) = viewModelScope.launch {
        character.value = characterRepo.getCharacterById(id = id)
    }
}
