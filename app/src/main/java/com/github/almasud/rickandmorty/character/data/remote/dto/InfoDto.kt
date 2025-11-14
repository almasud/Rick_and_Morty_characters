package com.github.almasud.rickandmorty.character.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class InfoDto(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("pages")
    @Expose
    val pages: Int,
    @SerializedName("next")
    @Expose
    val next: String,
    @SerializedName("prev")
    @Expose
    val prev: Any
)