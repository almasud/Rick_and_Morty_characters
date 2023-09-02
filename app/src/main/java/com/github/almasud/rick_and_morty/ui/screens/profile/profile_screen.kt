/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.domain.model.dummyCharacters
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme

@Composable
fun ProfileScreen(characterId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray.copy(alpha = 0.7f))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(width = 5.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF33BBFF), Color(0xFF3388FF)),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = dummyCharacters.find { it.id == characterId }?.image,
                contentDescription = stringResource(id = R.string.avatar), // Provide a meaningful description
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_image),
                error = painterResource(id = R.drawable.profile_image),
                fallback = painterResource(id = R.drawable.profile_image)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RickAndMortyTheme {
        ProfileScreen(characterId = "1")
    }
}