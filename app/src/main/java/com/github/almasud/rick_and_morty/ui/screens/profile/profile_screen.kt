/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.ui.theme.RickAndMortyTheme
import com.github.almasud.rick_and_morty.ui.utils.CharacterStatus
import com.github.almasud.rick_and_morty.ui.utils.shimmer

@Composable
fun ProfileScreen(viewModel: ProfileVM) {
    val characterState = viewModel.character

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray.copy(alpha = 0.3f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .padding(8.dp)
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
                .shimmer(visible = characterState.value == null)
        ) {
            AsyncImage(
                model = characterState.value?.image,
                contentDescription = stringResource(id = R.string.avatar), // Provide a meaningful description
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.profile_image),
                error = painterResource(id = R.drawable.profile_image),
                fallback = painterResource(id = R.drawable.profile_image)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                characterState.value?.let {
                    NameAndStatus(character = it, isLoading = characterState.value == null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                characterState.value?.let {
                    About(character = it, isLoading = characterState.value == null)
                }
            }
        }


    }
}

@Composable
private fun NameAndStatus(character: Character, isLoading: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = character.name,
                fontWeight = FontWeight.W800,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .defaultMinSize(minWidth = 150.dp)
                    .shimmer(visible = isLoading)
            )
            CharacterStatus(character = character, isLoading = isLoading)
        }
    }
}
@Composable
private fun About(character: Character, isLoading: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.about),
                fontWeight = FontWeight.W800,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .defaultMinSize(minWidth = 150.dp)
                    .shimmer(visible = isLoading)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AboutItem(label = stringResource(id = R.string.gender), character.gender, isLoading)
            Spacer(modifier = Modifier.height(8.dp))
            AboutItem(label = stringResource(id = R.string.species), character.species, isLoading)
            Spacer(modifier = Modifier.height(8.dp))
            AboutItem(label = stringResource(id = R.string.origin), character.origin.originName, isLoading)
            Spacer(modifier = Modifier.height(8.dp))
            AboutItem(label = stringResource(id = R.string.location), character.location.locationName, isLoading)
        }
    }
}

@Composable
private fun AboutItem(
    label: String,
    labelValue: String,
    isLoading: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .shimmer(visible = isLoading)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = labelValue,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .shimmer(visible = isLoading)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RickAndMortyTheme {
        ProfileScreen(viewModel())
    }
}