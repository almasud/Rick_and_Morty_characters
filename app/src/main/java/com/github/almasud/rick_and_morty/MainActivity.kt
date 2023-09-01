package com.github.almasud.rick_and_morty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.github.almasud.rick_and_morty.ui.screens.home.HomeScreenContainer
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
               HomeScreenContainer(navController = rememberNavController())
            }
        }
    }
}
