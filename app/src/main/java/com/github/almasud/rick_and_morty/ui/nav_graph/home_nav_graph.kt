/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 1/9/2023
 */

package com.github.almasud.rick_and_morty.ui.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.ui.NavItem
import com.github.almasud.rick_and_morty.ui.screens.character.CharacterScreenContainer
import com.github.almasud.rick_and_morty.ui.screens.character.CharacterVM
import com.github.almasud.rick_and_morty.ui.screens.character_details.CharacterDetailsScreenContainer
import com.github.almasud.rick_and_morty.ui.screens.character_details.CharacterDetailsVM
import timber.log.Timber

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = App.Constant.Navigation.Graph.HOME_GRAPH,
        startDestination = NavItem.Character.route
    ) {
        composable(route = NavItem.Character.route) {
            // Creates a ViewModel from the current BackStackEntry
            // Available in the androidx.hilt:hilt-navigation-compose artifact
            val characterVM = hiltViewModel<CharacterVM>()

            characterVM.navigateTo = { navRoute, singleTopMode, restoreSaveState ->
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

            CharacterScreenContainer(navController = navController, viewModel = characterVM)
        }

        composable(
            route = "${NavItem.CharacterDetails.route}/${App.Constant.Navigation.Argument.CHARACTER_ID}={${App.Constant.Navigation.Argument.CHARACTER_ID}}&${App.Constant.Navigation.Argument.CHARACTER_NAME}={${App.Constant.Navigation.Argument.CHARACTER_NAME}}",
            arguments = listOf(
                navArgument(App.Constant.Navigation.Argument.CHARACTER_ID) {
                    type = NavType.StringType
                })
        ) { navBackStackEntry ->
            val characterId =
                navBackStackEntry.arguments?.getString(App.Constant.Navigation.Argument.CHARACTER_ID)
            val characterName =
                navBackStackEntry.arguments?.getString(App.Constant.Navigation.Argument.CHARACTER_NAME)

            characterId?.let { id ->
                val viewModel = hiltViewModel<CharacterDetailsVM>()
                viewModel.getCharacterById(id.toLong())

                CharacterDetailsScreenContainer(
                    navController = navController, viewModel = viewModel, appTitle = characterName
                        ?: stringResource(
                            id = R.string.app_name
                        )
                )
            }
        }
    }
}