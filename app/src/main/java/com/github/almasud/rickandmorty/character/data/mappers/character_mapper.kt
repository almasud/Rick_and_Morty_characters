package com.github.almasud.rickandmorty.character.data.mappers

import com.github.almasud.rickandmorty.character.data.local.entities.CharacterEntity
import com.github.almasud.rickandmorty.character.data.local.entities.LocationEmbeddable
import com.github.almasud.rickandmorty.character.data.local.entities.OriginEmbeddable
import com.github.almasud.rickandmorty.character.data.remote.dto.CharacterDto
import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.character.domain.models.Location
import com.github.almasud.rickandmorty.character.domain.models.Origin

fun CharacterDto.toCharacterEntity() =
    CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = OriginEmbeddable(originDto.name),
        location = LocationEmbeddable(locationDto.name),
        image = image,
        created = created
    )

fun CharacterEntity.toDomain() =
    Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        origin = Origin(origin.originName),
        location = Location(location.locationName),
        image = image,
        created = created
    )
