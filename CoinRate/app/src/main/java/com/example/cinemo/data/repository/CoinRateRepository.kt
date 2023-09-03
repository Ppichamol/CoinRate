package com.example.cinemo.data.repository

import com.example.cinemo.data.model.CoinRateModelResponse

import retrofit2.Response
import retrofit2.http.GET

interface CoinRateRepository {
    @GET("/v1/bpi/currentprice.json")
    suspend fun getCoinRate(): Response<CoinRateModelResponse>
}