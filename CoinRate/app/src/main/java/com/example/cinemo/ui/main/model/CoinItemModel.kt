package com.example.cinemo.ui.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinItemModel(
    val code: String,
    val description: String,
    val rate: String,
    val rate_float: Double,
    val symbol: String,
    val time: String
) : Parcelable
