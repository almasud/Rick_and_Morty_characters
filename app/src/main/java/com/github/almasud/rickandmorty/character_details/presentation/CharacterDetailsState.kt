package com.github.almasud.rickandmorty.character_details.presentation

import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.core.presentation.models.UiText

data class CharacterDetailsState(
    val character: Character? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null
)
