package com.github.almasud.rickandmorty.character.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.almasud.rickandmorty.character.data.local.entities.CharacterRemoteKeyEntity

@Dao
interface CharacterRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(characterRemoteKeys: List<CharacterRemoteKeyEntity>)

    @Query("SELECT * FROM character_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyByCharacterId(id: Int): CharacterRemoteKeyEntity?

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearAllRemoteKeys()
}
