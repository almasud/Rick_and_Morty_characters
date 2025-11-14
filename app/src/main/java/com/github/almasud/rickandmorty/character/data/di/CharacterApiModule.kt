package com.github.almasud.rickandmorty.character.data.di

import com.github.almasud.rickandmorty.character.data.remote.api.ApiService
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
object CharacterApiModule {

    @Provides
    @Singleton
    @BaseUrl
    fun providesBaseUrl(): String = "https://rickandmortyapi.com/"

    @Provides
    @Singleton
    fun providesRetrofit(@BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrl
}