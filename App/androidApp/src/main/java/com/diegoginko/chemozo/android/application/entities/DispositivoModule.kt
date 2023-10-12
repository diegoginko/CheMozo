package com.diegoginko.chemozo.android.application.entities

import com.diegoginko.chemozo.android.repository.DispositivoRepository
import com.diegoginko.chemozo.android.room.AppDatabase
import com.diegoginko.chemozo.android.room.repository.DispositivoRepositoryRoomImpl
import org.koin.dsl.module

val dispositivoModule = module{
    factory<DispositivoRepository>{
        DispositivoRepositoryRoomImpl(get())
    }

    single { get<AppDatabase>().dispositivoDAO() }
}