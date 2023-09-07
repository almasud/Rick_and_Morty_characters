/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 7/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.almasud.rick_and_morty.ui.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavController,
    appBarTitle: String,
    snackbarHost: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                toolbarTitle = appBarTitle
            )
        },
        snackbarHost = { if (snackbarHost != null) snackbarHost() },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                content()
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, toolbarTitle: String) {
    val currentBackStackEntryState = navController.currentBackStackEntryAsState()
    val showBackButton = when (currentBackStackEntryState.value?.destination?.route) {
        NavItem.Character.route -> false
        else -> true
    }

    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W800,
                color = Color.Black
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
    )
}