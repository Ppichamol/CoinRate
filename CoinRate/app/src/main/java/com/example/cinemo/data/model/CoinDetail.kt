package com.example.cinemo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinDetail(
    val code: String,
    val description: String,
    val rate: String,
    val rate_float: Double,
    val symbol: String
) : Parcelable