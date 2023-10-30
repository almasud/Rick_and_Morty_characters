/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 15/10/2023
 */

package com.github.almasud.rick_and_morty.data.db

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.almasud.rick_and_morty.domain.model.Character
import com.github.almasud.rick_and_morty.domain.model.Location
import com.github.almasud.rick_and_morty.domain.model.Origin
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.abs

/**
 * Created by Md. Abdullah Al Masud on 15/10/23.
 * Email: dev.almasud@gmail.com
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class CharacterDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        characterDao = appDatabase.characterDao()
    }

    @After
    fun reset() {
        appDatabase.close()
    }

    @Test
    fun testInsertCharactersSuccess() = runTest {
        val totalItems = 100
        val itemsPerPage = 20
        val prevKey = 10
        val nextKey = prevKey + itemsPerPage
        val itemsAfter = abs(totalItems - nextKey)

        val insertableData = mutableListOf<Character>()
        repeat(totalItems) { index ->
            insertableData.add(
                Character(
                    id = index.toLong(),
                    "Mr. Doe",
                    status = "Alive",
                    species = "Human",
                    "Male",
                    origin = Origin("Earth"),
                    location = Location("Earth"),
                    image = ""
                )
            )
        }
        // Insert the list to Db
        characterDao.insertAll(insertableData)
        // Get list from Db
        val allCharactersPagingSource = characterDao.getCharacters()

        val expectedResultsData = mutableListOf<Character>()

        repeat(itemsPerPage) { index ->
            expectedResultsData.add(
                Character(
                    id = abs(nextKey - itemsPerPage) + index.toLong(),
                    "Mr. Doe",
                    status = "Alive",
                    species = "Human",
                    "Male",
                    origin = Origin("Earth"),
                    location = Location("Earth"),
                    image = ""
                )
            )
        }

        val expectedResult = PagingSource.LoadResult.Page(
            data = expectedResultsData,
            prevKey = prevKey,
            nextKey = nextKey,
            itemsBefore = prevKey,
            itemsAfter = itemsAfter
        )

        assertEquals(
            expectedResult, allCharactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = abs(nextKey - itemsPerPage),
                    loadSize = itemsPerPage,
                    placeholdersEnabled = false
                )
            )
        )
    }

}