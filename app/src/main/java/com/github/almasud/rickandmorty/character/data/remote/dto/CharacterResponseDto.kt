package com.github.almasud.rickandmorty.character.data.remote.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.google.gson.annotations.Expose

@Keep
data class CharacterResponseDto(
    @SerializedName("info")
    @Expose
    val infoDto: InfoDto,
    @SerializedName("results")
    @Expose
    val resultsDto: List<CharacterDto>
)