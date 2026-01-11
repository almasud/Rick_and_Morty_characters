package com.github.almasud.rickandmorty.character.data.remote.api

import android.util.Log
import com.github.almasud.rickandmorty.core.data.remote.models.RemoteResult
import com.github.almasud.rickandmorty.core.domain.models.DataError
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

suspend inline fun <T> handleRestApiCall(apiCall: suspend () -> Response<T>): RemoteResult<T, DataError.Remote> {
    val response = try {
        apiCall()
    } catch (e: Exception) {
        when (e) {
            is SocketTimeoutException ->
                return RemoteResult.Error(DataError.Remote.REQUEST_TIMEOUT)

            is UnresolvedAddressException, is UnknownHostException ->
                return RemoteResult.Error(DataError.Remote.NO_CONNECTION)

            else -> {
                Log.e("handleRestApiCall", "error: $e")
                currentCoroutineContext().ensureActive()
                return RemoteResult.Error(DataError.Remote.UNKNOWN)
            }
        }
    }

    return responseToResult(response)
}

fun <T> responseToResult(response: Response<T>): RemoteResult<T, DataError.Remote> {
    return when (response.code()) {
        in 200..299 -> {
            val body = response.body()
            if (body != null)
                RemoteResult.Success(body)
            else
                RemoteResult.Error(DataError.Remote.SERIALIZATION)
        }

        408 -> RemoteResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> RemoteResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> RemoteResult.Error(DataError.Remote.SERVER)
        else -> RemoteResult.Error(DataError.Remote.UNKNOWN)
    }
}

