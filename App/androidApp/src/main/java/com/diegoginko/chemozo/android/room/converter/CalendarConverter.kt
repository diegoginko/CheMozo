package com.diegoginko.chemozo.android.room.converter

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {

    @TypeConverter
    fun fromLong(value: Long?): GregorianCalendar? {
        if (value == null)
            return null
        val cal = GregorianCalendar()
        cal.time = Date(value)
        return cal
    }

    @TypeConverter
    fun toLong(calendar: GregorianCalendar?): Long? {
        val cal: Calendar? = calendar
        return cal?.timeInMillis
    }

}