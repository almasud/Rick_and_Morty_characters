/*
 * Copyright (c) 2023.
 * Created by: Abdullah Al Masud
 * Created on: 3/9/2023
 */

package com.github.almasud.rick_and_morty.data.api.resposne

import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val message: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()

    companion object {
        suspend fun <T : Any> handleRestApiResponse(
            execute: suspend () -> Response<T>
        ): NetworkResult<T> {
            return try {
                val response = execute()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Success(body)
                } else {
                    Error(code = response.code(), message = response.message())
                }
            } catch (e: HttpException) {
                Error(code = e.code(), message = e.message())
            } catch (e: Throwable) {
                Exception(e)
            }
        }
    }
}