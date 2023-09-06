/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 6/9/2023
 */

package com.github.almasud.rick_and_morty.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.almasud.rick_and_morty.BuildConfig
import com.github.almasud.rick_and_morty.domain.model.Character

@Database(
    entities = [Character::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CharacterDatabase::class.java, "${BuildConfig.APPLICATION_ID}.db"
            )
                .build()
    }
}