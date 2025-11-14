package com.github.almasud.rickandmorty.character.domain.models

import androidx.annotation.Keep

@Keep
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val created: String
)

@Keep
data class Origin(
    val originName: String
)

@Keep
data class Location(
    val locationName: String
)
