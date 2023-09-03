package com.example.cinemo.data.model

data class CoinRateModelResponse(
    val bpi: Bpi,
    val chartName: String,
    val disclaimer: String,
    val time: Time
)