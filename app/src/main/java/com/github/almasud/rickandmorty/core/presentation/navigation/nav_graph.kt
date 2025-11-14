package com.github.almasud.rickandmorty.core.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.almasud.rickandmorty.R
import com.github.almasud.rickandmorty.character_details.presentation.CharacterDetailsScreenContainer
import com.github.almasud.rickandmorty.character_details.presentation.CharacterDetailsVM
import com.github.almasud.rickandmorty.character.presentation.CharacterScreenContainer
import com.github.almasud.rickandmorty.character.presentation.CharacterVM

sealed class Graph(val route: String) {
    object Home : Graph(route = "home_graph")
}

@Composable
fun HomeNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Graph.Home.route,
        startDestination = NavItem.Character.route
    ) {
        composable(NavItem.Character.route) {
            val characterVM = hiltViewModel<CharacterVM>()

            characterVM.navigateTo = { navRoute, singleTopMode, restoreCurrentState ->
                navController.navigate(navRoute) {
                    Log.d(TAG, "HomeNavGraph: navRoute: $navRoute")
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }

                    launchSingleTop = singleTopMode
                    restoreState = restoreCurrentState
                }
            }
            CharacterScreenContainer(navController, characterVM)
        }

        composable(
            route = NavItem.CharacterDetails.route +
                    "?${NavItem.CharacterDetails.arguments[0]}={${NavItem.CharacterDetails.arguments[0]}}" +
                    "&${NavItem.CharacterDetails.arguments[1]}={${NavItem.CharacterDetails.arguments[1]}}",
            arguments = listOf(
                navArgument(NavItem.CharacterDetails.arguments[0]) {
                    type = NavType.IntType
                },
                navArgument(NavItem.CharacterDetails.arguments[1]) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val characterName =
                navBackStackEntry.arguments?.getString(NavItem.CharacterDetails.arguments[1])

            val characterDetailsVM = hiltViewModel<CharacterDetailsVM>()

            CharacterDetailsScreenContainer(
                navBarController = navController,
                characterDetailsVM = characterDetailsVM,
                appBarTitle = characterName ?: stringResource(R.string.app_name)
            )
        }
    }
}

private const val TAG = "nav_graph"
