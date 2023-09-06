/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.domain.model

import com.google.gson.annotations.Expose

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.google.gson.annotations.SerializedName

val dummyCharacter = Character(
    id = 1,
    name = "Mr. One",
    image = "https://picsum.photos/id/1/200/300",
    status = "Alive",
    species = "Specie One",
    gender = "Male",
    origin = Origin(originName = "Origin One"),
    location = Location(locationName = "Location One")
)

@Keep
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("species")
    @Expose
    val species: String,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("origin")
    @Expose
    @Embedded
    val origin: Origin,
    @SerializedName("location")
    @Expose
    @Embedded
    val location: Location,
    @SerializedName("image")
    @Expose
    val image: String
)

@Keep
data class Origin(
    @SerializedName("name")
    @Expose
    val originName: String
)

@Keep
data class Location(
    @SerializedName("name")
    @Expose
    val locationName: String
)
