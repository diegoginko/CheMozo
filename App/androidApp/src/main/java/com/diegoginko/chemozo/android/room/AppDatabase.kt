package com.diegoginko.chemozo.android.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diegoginko.chemozo.android.entidades.Dispositivo
import com.diegoginko.chemozo.android.room.converter.CalendarConverter
import com.diegoginko.chemozo.android.room.converter.DateTypeConverter
import com.diegoginko.chemozo.android.room.dao.DispositivoDAO

@Database(entities = [
    Dispositivo::class
                     ],version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter::class, CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dispositivoDAO() : DispositivoDAO
}