/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 4/9/2023
 */

package com.github.almasud.rick_and_morty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.almasud.rick_and_morty.ui.nav_graph.HomeNavGraph
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
               HomeNavGraph()
            }
        }
    }
}
