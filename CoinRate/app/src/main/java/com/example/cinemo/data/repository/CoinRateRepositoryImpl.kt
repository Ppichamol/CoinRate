package com.example.cinemo.data.repository

import com.example.cinemo.data.model.CoinRateModelResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinRateRepositoryImpl : CoinRateRepository {
    private val apiService = createRetrofit().create(CoinRateRepository::class.java)

    override suspend fun getCoinRate(): Response<CoinRateModelResponse> {
        return apiService.getCoinRate()
    }

    private fun createRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.coindesk.com")
        .client(
            OkHttpClient.Builder()
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}