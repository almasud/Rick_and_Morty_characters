package com.github.almasud.rickandmorty.character.data.di

import com.github.almasud.rickandmorty.character.data.sources.CharacterDataSource
import com.github.almasud.rickandmorty.character.data.sources.CharacterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CharacterSourceModule {

    @Binds
    @Singleton
    abstract fun bindDataSource(dataSourceImpl: CharacterDataSourceImpl): CharacterDataSource
}