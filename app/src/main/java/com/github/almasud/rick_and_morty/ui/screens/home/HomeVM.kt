/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.data.api.resposne.NetworkResult
import com.github.almasud.rick_and_morty.domain.repo.CharacterRepo
import com.github.almasud.rick_and_morty.ui.NavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val characterRepo: CharacterRepo
) : ViewModel() {
    lateinit var navigateTo: (navRoute: String, singleTopMode: Boolean, restoreSaveState: Boolean) -> Unit

    fun showProfileScreen(characterId: Int) {
        navigateTo(
            "${NavItem.Profile.route}/${App.Constant.Navigation.Argument.CHARACTER_ID}=$characterId",
            true, false
        )
    }

    suspend fun loadCharacters() {
        Timber.d("loadCharacters: is called")
        when(val response = characterRepo.getCharacters(page = 1)) {
            is NetworkResult.Success -> {
                Timber.d("response: ${response.data.characters}")
            }
            is NetworkResult.Error -> Timber.e("loadCharacters: error: ${response.message}")
            is NetworkResult.Exception -> Timber.e(response.e, "loadCharacters: exception: ${response.e.message}")
        }
    }

    init {
        viewModelScope.launch {
            loadCharacters()
        }
    }
}