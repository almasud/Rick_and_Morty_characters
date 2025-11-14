package com.github.almasud.rickandmorty.character.data.di

import com.github.almasud.rickandmorty.character.data.repositories.CharacterRepoImpl
import com.github.almasud.rickandmorty.character.domain.repositories.CharacterRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterRepoModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepo(impl: CharacterRepoImpl): CharacterRepo
}