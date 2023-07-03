package com.diegoginko.chemozo.android.di

import com.diegoginko.chemozo.android.ui.broker.adapter.DispositivosAdapter
import org.koin.dsl.module

val adapterModule = module {
    fun provideDispositivosAdapter() : DispositivosAdapter{
        return DispositivosAdapter()
    }

    single{
        provideDispositivosAdapter()
    }
}