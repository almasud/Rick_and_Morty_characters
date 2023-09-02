/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.home

import androidx.lifecycle.ViewModel
import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.ui.NavItem

class HomeVM : ViewModel() {
    lateinit var navigateTo: (navRoute: String, singleTopMode: Boolean, restoreSaveState: Boolean) -> Unit

    fun showProfileScreen(characterId: Int) {
        navigateTo(
            "${NavItem.Profile.route}/${App.Constant.Navigation.Argument.CHARACTER_ID}=$characterId",
            true, false
        )
    }

}