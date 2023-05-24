package com.diegoginko.chemozo.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        val driver =""
        // AndroidSqliteDriver(PeopleInSpaceDatabase.Schema, get(), "peopleinspace.db")

        //PeopleInSpaceDatabaseWrapper(PeopleInSpaceDatabase(driver))
    }
//    single { Android.create() }
}