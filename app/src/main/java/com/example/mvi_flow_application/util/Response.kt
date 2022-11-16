package com.example.mvi_flow_application.util

sealed class Response<out Result, out Error> {

    data class Success<Result>(
            val result: Result
    ) : Response<Result, Nothing>()

    data class Error<Error>(
            val error: Error
    ) : Response<Nothing, Error>()

    companion object {
        val SuccessUnit = Success(Unit)
        val ErrorUnit = Error(Unit)
    }
}
