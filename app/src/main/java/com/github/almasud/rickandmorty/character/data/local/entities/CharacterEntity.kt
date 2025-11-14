package com.github.almasud.rickandmorty.character.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    @Embedded(prefix = "origin_")
    val origin: OriginEmbeddable,
    @Embedded(prefix = "location_")
    val location: LocationEmbeddable,
    val image: String,
    val created: String
)

data class OriginEmbeddable(
    val originName: String
)

data class LocationEmbeddable(
    val locationName: String
)
