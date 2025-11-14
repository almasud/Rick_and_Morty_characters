package com.github.almasud.rickandmorty.character.domain.usecases

import androidx.paging.PagingData
import com.github.almasud.rickandmorty.character.domain.models.Character
import com.github.almasud.rickandmorty.character.domain.repositories.CharacterRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepo: CharacterRepo
) {
    operator fun invoke(): Flow<PagingData<Character>> = characterRepo.getCharacters()
}
