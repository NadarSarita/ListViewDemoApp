package com.example.demoapp.core

import android.util.Log
import androidx.annotation.MainThread
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.EOFException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

abstract class NetworkToUIProvider<RESULT> {

    fun asFlow() = flow<State<RESULT>> {

        emit(State.Loading())

        val apiResponse = fetchFromRemote()
        val errorBody = apiResponse.errorBody()

        val responseBody = apiResponse.body()

        if (apiResponse.isSuccessful && responseBody != null) {
            emit(State.Success(responseBody))
        } else {
            when (apiResponse.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> emit(
                    State.ResponseError(
                        errorCode = apiResponse.code(),
                        message = "Incorrect credentials",
                        errorBody = errorBody
                    )
                )
                HttpURLConnection.HTTP_BAD_GATEWAY -> emit(
                    State.ResponseError(
                        errorCode = apiResponse.code(),
                        message = "Server is down",
                        errorBody = errorBody
                    )
                )
                else -> emit(
                    State.ResponseError(
                        errorCode = apiResponse.code(),
                        message = parseError(errorBody),
                        errorBody = errorBody
                    )
                )
            }
        }

    }.catch { e ->
        Log.e("TAG", "Error -> $e")
        when (e) {
            is IllegalStateException -> emit(
                State.ExceptionError(
                    errorMessage = "Oops! Error with data parsing",
                    throwable = e
                )
            )
            is SocketTimeoutException -> emit(
                State.ExceptionError(
                    errorMessage = "Request Timeout! try again later",
                    throwable = e
                )
            )
            is EOFException -> emit(
                State.ExceptionError(
                    errorMessage = "Response with no body",
                    throwable = e
                )
            )
            else -> emit(
                State.ExceptionError(
                    errorMessage = "Some Error Occurred! Can't get the latest data",
                    throwable = e
                )
            )
        }
        e.printStackTrace()
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RESULT>
}