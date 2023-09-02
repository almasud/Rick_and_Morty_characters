/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.ui.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.almasud.rick_and_morty.R
import com.github.almasud.rick_and_morty.domain.model.Character

@Composable
fun CharacterStatus(character: Character, isLoading: Boolean = false) {
    Row(
        modifier = Modifier
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = stringResource(
                id = R.string.status
            ),
            modifier = Modifier
                .size(20.dp)
                .shimmer(visible = isLoading),
            tint = if (character.status.lowercase() == stringResource(id = R.string.alive).lowercase()) Color.Green.copy(
                alpha = 0.5f
            ) else Color.Red.copy(alpha = 0.5f)
        )
        Text(
            text = character.status,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 4.dp)
                .shimmer(visible = isLoading)
        )
    }
}