package com.diegoginko.chemozo.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

fun sharedModule(enableNetworkLogs: Boolean) = module {

}