package com.diegoginko.chemozo.android.application

import android.app.Application
import com.diegoginko.chemozo.android.di.appModule
import com.diegoginko.chemozo.di.initKoin
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CheMozo : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CheMozo)
            modules(appModule)
        }

    }
}