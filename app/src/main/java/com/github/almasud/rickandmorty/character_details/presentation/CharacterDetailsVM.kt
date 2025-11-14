package com.github.almasud.rickandmorty.character_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.almasud.rickandmorty.R
import com.github.almasud.rickandmorty.character.domain.usecases.GetCharacterDetailsUseCase
import com.github.almasud.rickandmorty.core.presentation.models.UiText
import com.github.almasud.rickandmorty.core.presentation.navigation.NavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterDetailsVM @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase
) : ViewModel() {

    private val characterId: Int =
        checkNotNull(savedStateHandle[NavItem.CharacterDetails.arguments[0]]) {
            "Character id is required"
        }

    private val _state = MutableStateFlow(CharacterDetailsState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        fetchCharacter()
    }

    fun retry() {
        fetchCharacter()
    }

    private fun fetchCharacter() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        val character = getCharacterDetailsUseCase(characterId)
        if (character != null) {
            _state.update { it.copy(character = character, isLoading = false, error = null) }
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = UiText.StringResourceId(R.string.character_not_found)
                )
            }
        }
    }
}
