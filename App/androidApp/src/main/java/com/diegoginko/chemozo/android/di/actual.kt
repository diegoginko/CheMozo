package com.diegoginko.chemozo.android.di

import android.content.Context
import com.diegoginko.chemozo.utils.Platform
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {

    fun providePlatform(context: Context) : Platform {
        return Platform(context)
    }

    single { providePlatform(androidContext()) }
}