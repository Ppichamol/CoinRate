package com.example.cinemo.ui.main

import android.os.Looper
import android.text.Html
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.viewModelScope
import com.example.cinemo.base.BaseViewModel
import com.example.cinemo.common.SingleLiveEvent
import com.example.cinemo.common.toResult
import com.example.cinemo.data.ApiResult
import com.example.cinemo.data.repository.CoinRateRepository
import com.example.cinemo.ui.main.adapter.CoinItem
import com.example.cinemo.ui.main.model.CoinItemModel
import kotlinx.coroutines.launch

class AllCurrencyViewModel (private val coinRepo : CoinRateRepository) : BaseViewModel() {

    var coinItems = SingleLiveEvent<List<CoinItem>>()
        private set

    var validateText = SingleLiveEvent<Boolean>()
    var btcAmountLiveData = SingleLiveEvent<Double>()

    var btcAmount = BTC_DEFAULT_AMOUNT


    private val coinItemsList: MutableList<CoinItem> = mutableListOf()
    private var targetTime: Long = 0
    private val handler = HandlerCompat.createAsync(Looper.getMainLooper())

    private var usdRate: Double = 0.00
    private var gbpRate: Double = 0.00
    private var eurRate: Double = 0.00

    companion object {
        private val usdItemsList: MutableList<CoinItem> = mutableListOf()
        private val gbpItemsList: MutableList<CoinItem> = mutableListOf()
        private val eurItemsList: MutableList<CoinItem> = mutableListOf()
        private const val ONE_MINUTE = 60000L
        const val BTC_DEFAULT_AMOUNT = 0.0000000
        private var time = ""
    }

    private val startCountDownTimer = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            if (currentTime > targetTime) {
                getAllCoinList()
                stopJobTimer()
            } else {
                handler.postDelayed(this, 1000L)
            }
        }
    }


    fun getAllCoinList() {
        viewModelScope.launch {
            if(coinItemsList.isNotEmpty()) coinItemsList.clear()
            when(val result = coinRepo.getCoinRate().toResult()){
                is ApiResult.Success -> {
                    startJobTimer()
                    val eur = CoinItemModel(
                        code = result.data.bpi.EUR.code,
                        description = result.data.bpi.EUR.description,
                        rate = result.data.bpi.EUR.rate,
                        rate_float = result.data.bpi.EUR.rate_float,
                        symbol = Html
                            .fromHtml(result.data.bpi.EUR.symbol, Html.FROM_HTML_MODE_COMPACT)
                            .toString(),
                        time = result.data.time.updated
                    )
                    val usd = CoinItemModel(
                        code = result.data.bpi.USD.code,
                        description = result.data.bpi.USD.description,
                        rate = result.data.bpi.USD.rate,
                        rate_float = result.data.bpi.USD.rate_float,
                        symbol = Html
                            .fromHtml(result.data.bpi.USD.symbol, Html.FROM_HTML_MODE_COMPACT)
                            .toString(),
                        time = result.data.time.updated
                    )
                    val gbp = CoinItemModel(
                        code = result.data.bpi.GBP.code,
                        description = result.data.bpi.GBP.description,
                        rate = result.data.bpi.GBP.rate,
                        rate_float = result.data.bpi.GBP.rate_float,
                        symbol = Html
                            .fromHtml(result.data.bpi.GBP.symbol, Html.FROM_HTML_MODE_COMPACT)
                            .toString(),
                        time = result.data.time.updated
                    )

                    gbpRate = result.data.bpi.GBP.rate_float
                    usdRate = result.data.bpi.USD.rate_float
                    eurRate = result.data.bpi.EUR.rate_float

                    coinItemsList.add(CoinItem.CoinItemDetail(usd))
                    coinItemsList.add(CoinItem.CoinItemDetail(gbp))
                    coinItemsList.add(CoinItem.CoinItemDetail(eur))

                    if (result.data.time.updated != time) {
                        time = result.data.time.updated
                        usdItemsList.add(CoinItem.CoinItemDetail(usd))
                        gbpItemsList.add(CoinItem.CoinItemDetail(gbp))
                        eurItemsList.add(CoinItem.CoinItemDetail(eur))
                    }


                    coinItems.value = coinItemsList
                }
                is ApiResult.Error -> {
                    Log.d("M_DEBUG","response: ${result.errorMessage}")
                }
            }
        }
    }

    private fun startJobTimer() {
        targetTime = System.currentTimeMillis() + ONE_MINUTE
        handler.postDelayed(startCountDownTimer, 1000L)
    }

    private fun stopJobTimer() {
        handler.removeCallbacks(startCountDownTimer)
    }

    fun selectedCoin(currency: String) : Array<CoinItem> {
        var arrayCoin : Array<CoinItem> = arrayOf()
        when (currency) {
            "USD" -> { arrayCoin = usdItemsList.toTypedArray() }
            "GBP" -> { arrayCoin = gbpItemsList.toTypedArray() }
            "EUR" -> { arrayCoin = eurItemsList.toTypedArray() }
        }
        return arrayCoin
    }

    fun validateTextField (input: String) {
        validateText.value = input.isNotEmpty()
    }

    fun calculateBtc(currency: String, amount: Double) {
        when (currency) {
            "USD" -> { btcAmountLiveData.value = amount/usdRate }
            "GBP" -> { btcAmountLiveData.value = amount/gbpRate }
            "EUR" -> { btcAmountLiveData.value = amount/eurRate }
        }
        btcAmount = btcAmountLiveData.value!!
    }
}