package com.example.cinemo.di

import com.example.cinemo.data.repository.CoinRateRepository
import com.example.cinemo.data.repository.CoinRateRepositoryImpl
import com.example.cinemo.ui.coin_history.CoinHistoryViewModel
import com.example.cinemo.ui.main.AllCurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    factory<CoinRateRepository> { CoinRateRepositoryImpl() }
    viewModel { AllCurrencyViewModel(get())}
    viewModel { CoinHistoryViewModel() }
}