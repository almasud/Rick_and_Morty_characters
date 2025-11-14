package com.github.almasud.rickandmorty.core.domain.models

sealed interface DataError {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_CONNECTION,
        SERIALIZATION,
        SERVER,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }
}