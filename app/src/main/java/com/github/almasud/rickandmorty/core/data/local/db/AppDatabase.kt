package com.github.almasud.rickandmorty.core.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.almasud.rickandmorty.BuildConfig
import com.github.almasud.rickandmorty.character.data.local.db.dao.CharacterDao
import com.github.almasud.rickandmorty.character.data.local.db.dao.CharacterRemoteKeyDao
import com.github.almasud.rickandmorty.character.data.local.entities.CharacterEntity
import com.github.almasud.rickandmorty.character.data.local.entities.CharacterRemoteKeyEntity

@Database(
    entities = [CharacterEntity::class, CharacterRemoteKeyEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun remoteKeyDao(): CharacterRemoteKeyDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "${BuildConfig.APPLICATION_ID}.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}
