package com.github.almasud.rickandmorty.character.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_remote_keys")
data class CharacterRemoteKeyEntity(
    @PrimaryKey val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
