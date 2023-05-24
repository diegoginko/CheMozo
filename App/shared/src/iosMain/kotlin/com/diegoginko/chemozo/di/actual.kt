package com.diegoginko.chemozo.di

import org.koin.dsl.module

actual fun platformModule() = module {

    single {
        com.diegoginko.chemozo.utils.Platform()
    }
}