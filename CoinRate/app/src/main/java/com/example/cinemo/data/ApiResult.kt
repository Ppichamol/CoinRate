package com.example.cinemo.data


sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val exception: Exception?, val errorMessage: String? = null) :
        ApiResult<Nothing>()
}