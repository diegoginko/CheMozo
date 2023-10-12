package com.diegoginko.chemozo.android.application

import androidx.room.Room
import com.diegoginko.chemozo.android.room.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "che-mozo-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}