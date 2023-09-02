/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 1/9/2023
 */

package com.github.almasud.rick_and_morty.ui.nav_graph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.ui.NavItem
import com.github.almasud.rick_and_morty.ui.screens.home.CharactersScreen
import com.github.almasud.rick_and_morty.ui.screens.home.HomeVM
import com.github.almasud.rick_and_morty.ui.screens.profile.ProfileScreen
import timber.log.Timber

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = App.Constant.Navigation.Graph.HOME_GRAPH,
        startDestination = NavItem.Home.route
    ) {
        composable(route = NavItem.Home.route) {
            val homeVM = viewModel<HomeVM>()

            homeVM.navigateTo = { navRoute, singleTopMode, restoreSaveState ->
                navController.navigate(navRoute) {
                    Timber.i("HomeNavGraph: navRoute: $navRoute")
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    // Avoid multiple copies of the same destination

                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            Timber.i("HomeNavGraph: popUpTo: route: $route")
                            saveState = true
                        }
                    }

                    // Re-selecting the same item
                    launchSingleTop = singleTopMode
                    // Restore state when re-selecting a previously selected item
                    restoreState = restoreSaveState
                }
            }

            CharactersScreen(homeVM = homeVM)
        }

        composable(
            route = "${NavItem.Profile.route}/${App.Constant.Navigation.Argument.CHARACTER_ID}={${App.Constant.Navigation.Argument.CHARACTER_ID}}",
            arguments = listOf(
                navArgument(App.Constant.Navigation.Argument.CHARACTER_ID) {
                    type = NavType.StringType
                })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(App.Constant.Navigation.Argument.CHARACTER_ID)?.let {
                ProfileScreen(characterId = it)
            }
        }
    }
}