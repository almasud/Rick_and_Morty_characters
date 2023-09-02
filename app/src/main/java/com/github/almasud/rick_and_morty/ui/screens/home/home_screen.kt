/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 1/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.domain.model.dummyCharacters
import com.github.almasud.rick_and_morty.ui.NavItem
import com.github.almasud.rick_and_morty.ui.nav_graph.HomeNavGraph
import com.github.almasud.rick_and_morty.ui.utils.CharacterStatus
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme
import com.github.almasud.rick_and_morty.ui.utils.shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContainer(navController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                HomeNavGraph(navController = navController)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val currentBackStackEntryState = navController.currentBackStackEntryAsState()
    val showBackButton = when (currentBackStackEntryState.value?.destination?.route) {
        NavItem.Home.route -> false
        else -> true
    }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
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

@Composable
fun CharactersScreen(homeVM: HomeVM) {
    val coroutineScope = rememberCoroutineScope()
    val isLoading = false

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray.copy(alpha = 0.3f))
            .padding(8.dp)
    ) {
        LazyColumn(
            content = {
                items(items = dummyCharacters, itemContent = { character ->
                    CharacterItem(character = character, isLoading = isLoading) {
                        coroutineScope.launch {
                            homeVM.showProfileScreen(characterId = it)
                        }
                    }
                })
            })
    }
}

@Composable
fun CharacterItem(
    character: Character,
    isLoading: Boolean = false,
    onItemClickListener: ((Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp)
            .clickable(enabled = !isLoading) {
                onItemClickListener?.invoke(character.id)
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Circular user image on the right side
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF33BBFF), Color(0xFF3388FF)),
                                start = Offset.Zero,
                                end = Offset.Infinite
                            )
                        )
                        .align(Alignment.CenterVertically)
                        .shimmer(visible = isLoading)
                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = stringResource(id = R.string.avatar), // Provide a meaningful description
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.profile_image),
                        error = painterResource(id = R.drawable.profile_image),
                        fallback = painterResource(id = R.drawable.profile_image)
                    )
                }

                // User information on the left side
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = character.name,
                        fontWeight = FontWeight.W800,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .wrapContentSize()
                            .defaultMinSize(minWidth = 150.dp)
                            .shimmer(visible = isLoading)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CharacterStatus(character = character, isLoading = isLoading)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RickAndMortyTheme {
        HomeScreenContainer()
    }
}