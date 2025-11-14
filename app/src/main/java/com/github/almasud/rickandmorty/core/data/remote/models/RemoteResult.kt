package com.github.almasud.rickandmorty.core.data.remote.models

import com.github.almasud.rickandmorty.core.domain.models.DataError

sealed class RemoteResult<out D, out E> {
    data class Success<out D>(val data: D) : RemoteResult<D, Nothing>()
    data class Error<out E : DataError>(val error: E) : RemoteResult<Nothing, E>()
}

inline fun <T, E : DataError.Remote, R> RemoteResult<T, E>.map(transform: (T) -> R): RemoteResult<R, E> =
    when (this) {
        is RemoteResult.Success -> RemoteResult.Success(transform(data))
        is RemoteResult.Error -> RemoteResult.Error(error)
    }

inline fun <T, E : DataError.Remote> RemoteResult<T, E>.onSuccess(action: (T) -> Unit): RemoteResult<T, E> =
    when (this) {
        is RemoteResult.Success -> {
            action(data)
            this
        }

        is RemoteResult.Error -> this
    }

inline fun <T, E : DataError.Remote> RemoteResult<T, E>.onError(action: (E) -> Unit): RemoteResult<T, E> =
    when (this) {
        is RemoteResult.Success -> this
        is RemoteResult.Error -> {
            action(error)
            this
        }
    }
