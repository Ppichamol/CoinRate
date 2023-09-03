package com.example.cinemo.common

import com.example.cinemo.data.ApiResult
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Response

fun <T : Any> Response<T>.toResult(): ApiResult<T> {
    try {
        if (this.isSuccessful) {
            this.body()?.let {
                return ApiResult.Success(it)
            } ?: run {
                return ApiResult.Error(this.code(), null)
            }
        } else {
            return if (this.code() == 400) {
                val errorMessage = errorBody().getErrorMessage()
                ApiResult.Error(this.code(), null, errorMessage)
            } else {
                ApiResult.Error(this.code(), null)
            }
        }
    } catch (e: Exception) {
        return ApiResult.Error(this.code(), e)
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    try {
        val errorBody = this
        errorBody?.charStream()?.readText()
            ?.apply { return JsonParser().parse(this).asJsonObject.get("message").asString }
    } catch (e: Exception) {
        return null
    }

    return null
}