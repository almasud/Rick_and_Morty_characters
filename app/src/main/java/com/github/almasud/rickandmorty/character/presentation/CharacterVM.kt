package com.github.almasud.rickandmorty.character.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.character.domain.usecases.GetCharactersUseCase
import com.github.almasud.rickandmorty.core.presentation.navigation.NavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharacterVM @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope)

    lateinit var navigateTo: (navRoute: String, singleTopMode: Boolean, restoreCurrentState: Boolean) -> Unit

    fun showCharacterDetails(character: Character) {
        navigateTo(
            NavItem.CharacterDetails.route +
                    "?${NavItem.CharacterDetails.arguments[0]}=${character.id}" +
                    "&${NavItem.CharacterDetails.arguments[1]}=${character.name}",
            true,
            false
        )
    }
}
