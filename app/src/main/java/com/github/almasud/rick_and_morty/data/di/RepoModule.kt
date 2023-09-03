/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 4/9/2023
 */

package com.github.almasud.rick_and_morty.data.di

import com.github.almasud.rick_and_morty.data.repo.CharacterRepoImpl
import com.github.almasud.rick_and_morty.domain.repo.CharacterRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindCharacterRepo(
        characterRepoImpl: CharacterRepoImpl
    ): CharacterRepo
}