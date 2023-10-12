package com.diegoginko.chemozo.android.di

import com.diegoginko.chemozo.android.ui.broker.BrokerViewModel
import com.diegoginko.chemozo.android.ui.client.ClientViewModel
import com.diegoginko.chemozo.android.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalUnsignedTypes::class)
val appModule = module{
    viewModel { MainViewModel() }
    viewModel { ClientViewModel() }
    viewModel { BrokerViewModel(get()) }
}