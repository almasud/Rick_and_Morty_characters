package com.github.almasud.rickandmorty.character.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class CharacterDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("species")
    @Expose
    val species: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("origin")
    @Expose
    val originDto: OriginDto,
    @SerializedName("location")
    @Expose
    val locationDto: LocationDto,
    @SerializedName("image")
    @Expose
    val image: String,
    @SerializedName("episode")
    @Expose
    val episode: List<String>,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("created")
    @Expose
    val created: String
)