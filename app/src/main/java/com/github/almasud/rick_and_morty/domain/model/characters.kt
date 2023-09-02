/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 2/9/2023
 */

package com.github.almasud.rick_and_morty.domain.model
import android.os.Parcelable
import com.google.gson.annotations.Expose

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

val dummyCharacters = listOf(
    Character(
        id = 1,
        name = "Mr. One",
        image = "https://picsum.photos/id/1/200/300",
        status = "Alive",
        species = "Specie One",
        gender = "Male",
        origin = Origin(name = "Origin One"),
        location = Location(name = "Location One")
    ),
    Character(
        id = 2,
        name = "Mr. One",
        image = "https://picsum.photos/id/2df/200/300",
        status = "Alive",
        species = "Specie One",
        gender = "Male",
        origin = Origin(name = "Origin One"),
        location = Location(name = "Location One")
    ),
    Character(
        id = 3,
        name = "Mr. Two",
        image = "https://picsum.photos/id/3/200/300",
        status = "Dead",
        species = "Specie Two",
        gender = "Male",
        origin = Origin(name = "Origin Two"),
        location = Location(name = "Location Two")
    ),
    Character(
        id = 4,
        name = "Mrs. One",
        image = "https://picsum.photos/id/4/200/300",
        status = "Alive",
        species = "Specie One",
        gender = "Female",
        origin = Origin(name = "Origin One"),
        location = Location(name = "Location One")
    ),
    Character(
        id = 5,
        name = "Mr. Three",
        image = "https://picsum.photos/id/1/200/300",
        status = "Alive",
        species = "Specie Three",
        gender = "Male",
        origin = Origin(name = "Origin Three"),
        location = Location(name = "Location Three")
    ),
    Character(
        id = 6,
        name = "Mrs. Two",
        image = "https://picsum.photos/id/5/200/300",
        status = "Dead",
        species = "Specie Two",
        gender = "Female",
        origin = Origin(name = "Origin Two"),
        location = Location(name = "Location Two")
    )
)

@Parcelize
@Keep
data class Characters(
    @SerializedName("info")
    @Expose
    val info: Info,
    @SerializedName("results")
    @Expose
    val characters: List<Character>
) : Parcelable

@Parcelize
@Keep
data class Info(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("next")
    @Expose
    val next: Int?,
    @SerializedName("prev")
    @Expose
    val prev: Int?,
    @SerializedName("pages")
    @Expose
    val pages: Int
) : Parcelable

@Parcelize
@Keep
data class Character(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("image")
    @Expose
    val image: String,
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
    val origin: Origin,
    @SerializedName("location")
    @Expose
    val location: Location
) : Parcelable

@Parcelize
@Keep
data class Origin(
    @SerializedName("name")
    @Expose
    val name: String
) : Parcelable

@Parcelize
@Keep
data class Location(
    @SerializedName("name")
    @Expose
    val name: String
) : Parcelable