package com.github.almasud.rickandmorty.character.domain.usecases

import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.character.domain.repositories.CharacterRepo
import javax.inject.Inject

class GetCharacterDetailsUseCase @Inject constructor(
    private val characterRepo: CharacterRepo
) {
    suspend operator fun invoke(id: Int): Character? = characterRepo.getCharacter(id)
}
