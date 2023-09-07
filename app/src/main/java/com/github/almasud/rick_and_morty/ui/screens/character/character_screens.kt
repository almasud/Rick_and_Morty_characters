/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 1/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.character

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.domain.model.dummyCharacter
import com.github.almasud.rick_and_morty.ui.nav_graph.HomeNavGraph
import com.github.almasud.rick_and_morty.ui.screens.AppScaffold
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme
import com.github.almasud.rick_and_morty.ui.utils.CharacterStatus
import com.github.almasud.rick_and_morty.ui.utils.shimmer
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun CharacterScreenContainer(navController: NavController, viewModel: CharacterVM) {
    AppScaffold(
        navController = navController,
        appBarTitle = stringResource(id = R.string.app_name)
    ) {
        CharactersScreen(viewModel = viewModel)
    }
}

@Composable
fun CharactersScreen(viewModel: CharacterVM) {
    val coroutineScope = rememberCoroutineScope()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray.copy(alpha = 0.3f))
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if ((characters.itemSnapshotList.isEmpty())
            && characters.loadState.refresh !is LoadState.Loading
        ) {
            Timber.i("CharacterScreen: characters is empty!")
            Text(
                text = stringResource(id = R.string.no_data_found),
                color = Color.LightGray,
                textAlign = TextAlign.Center,
            )
        } else {
            Timber.i("CharacterScreen: characters is not empty!")
            LazyColumn {
                itemsIndexed(characters) { _, character ->
                    character?.let {
                        CharacterItem(character = it) {
                            coroutineScope.launch {
                                viewModel.showProfileScreen(character = it)
                            }
                        }
                    }
                }
            }
        }
        characters.apply {
            when {
                loadState.refresh is LoadState.Loading
                        || loadState.prepend is LoadState.Loading
                        || loadState.append is LoadState.Loading -> {
                    repeat(10) {
                        Timber.i("CharacterScreen: is loading...")
                        CharacterItem(character = dummyCharacter, isLoading = true)
                    }
                }

                loadState.refresh is LoadState.Error
                        || loadState.prepend is LoadState.Error
                        || loadState.append is LoadState.Error -> {
                    val errorState = if (loadState.refresh is LoadState.Error)
                        (loadState.refresh as LoadState.Error)
                    else if (loadState.prepend is LoadState.Error)
                        (loadState.prepend as LoadState.Error)
                    else
                        (loadState.append as LoadState.Error)

                    Timber.e(
                        errorState.error,
                        "CharacterScreen: ${errorState.error.message}"
                    )

                    LaunchedEffect(key1 = errorState) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.failed_to_load_characters_data),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    isLoading: Boolean = false,
    onItemClickListener: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 8.dp)
            .clickable(enabled = !isLoading) {
                onItemClickListener?.invoke()
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
        HomeNavGraph()
    }
}