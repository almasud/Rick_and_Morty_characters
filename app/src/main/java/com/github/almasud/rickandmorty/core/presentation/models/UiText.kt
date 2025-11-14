package com.github.almasud.rickandmorty.core.presentation.models

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringResourceId(@StringRes val id: Int, val args: List<Any> = emptyList()) : UiText

    @Composable
    fun asString(): String =
        when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(id, args)
        }


    fun asString(context: Context): String =
        when (this) {
            is DynamicString -> value
            is StringResourceId -> context.getString(id, args)
        }
}