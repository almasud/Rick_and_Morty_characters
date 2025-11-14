package com.github.almasud.rickandmorty.core.presentation.extensions

import com.github.almasud.rickandmorty.R
import com.github.almasud.rickandmorty.core.domain.models.DataError
import com.github.almasud.rickandmorty.core.presentation.models.UiText

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> R.string.request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> R.string.too_many_requests
        DataError.Remote.NO_CONNECTION -> R.string.no_connection
        DataError.Remote.SERIALIZATION -> R.string.data_parse_error
        DataError.Remote.SERVER -> R.string.server_error
        DataError.Remote.UNKNOWN -> R.string.unknown_error
        DataError.Local.DISK_FULL -> R.string.disk_full_error
        DataError.Local.UNKNOWN -> R.string.unknown_error
    }

    return UiText.StringResourceId(stringRes)
}