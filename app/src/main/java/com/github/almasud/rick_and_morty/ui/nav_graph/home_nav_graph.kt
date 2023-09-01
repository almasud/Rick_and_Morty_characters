/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 1/9/2023
 */

package com.github.almasud.rick_and_morty.ui.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.ui.NavItem
import com.github.almasud.rick_and_morty.ui.screens.home.HomeScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = App.Constant.Navigation.Graph.HOME_GRAPH,
        startDestination = NavItem.Home.route
    ) {
        composable(route = NavItem.Home.route) {
            HomeScreen()
        }
    }
}