package com.github.almasud.rickandmorty.core.data.local.db

import android.content.Context
import com.github.almasud.rickandmorty.character.data.local.db.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    fun providesCharacterDao(appDatabase: AppDatabase): CharacterDao =
        appDatabase.characterDao()
}