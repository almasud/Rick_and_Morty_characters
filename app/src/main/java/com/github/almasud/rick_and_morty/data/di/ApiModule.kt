/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 3/9/2023
 */

package com.github.almasud.rick_and_morty.data.di

import com.github.almasud.rick_and_morty.App
import com.github.almasud.rick_and_morty.data.api.CharacterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    @UrlBase
    fun providesBaseUrl() : String =
        App.Constant.Api.BASE_URL

    @Provides
    @Singleton
    fun providesRetrofitBuilder(@UrlBase baseUrl: String) : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesCharacterApiService(retrofit: Retrofit): CharacterApiService =
        retrofit.create(CharacterApiService::class.java)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UrlBase
}