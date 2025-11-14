package com.github.almasud.rickandmorty.core.presentation.navigation

import androidx.annotation.StringRes
import com.github.almasud.rickandmorty.R

sealed class NavItem(
    @StringRes val title: Int,
    val route: String
) {
    object Character : NavItem(R.string.app_name, "character")
    object CharacterDetails : NavItem(R.string.character_details, "character_details") {
        val arguments = listOf(
            "characterId",
            "characterName"
        )
    }
}