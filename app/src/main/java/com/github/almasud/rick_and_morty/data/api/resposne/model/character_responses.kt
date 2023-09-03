/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 3/9/2023
 */

package com.github.almasud.rick_and_morty.data.api.resposne.model

import androidx.annotation.Keep
import com.github.almasud.rick_and_morty.domain.model.Character
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class CharactersResponse(
    @SerializedName("info")
    @Expose
    val info: Info,
    @SerializedName("results")
    @Expose
    val characters: List<Character>
)

@Keep
data class Info(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("pages")
    @Expose
    val pages: Int,
    @SerializedName("next")
    @Expose
    val next: String?,
    @SerializedName("prev")
    @Expose
    val prev: String?
)
