package com.diegoginko.chemozo.android.room.converter

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
//    @TypeConverter
//    fun toDate(stringdate: String?): Date? {
//        return stringdate?.let { DateFormat.getInstance().parse(it) }
//    }
//
//
//    @SuppressLint("SimpleDateFormat")
//    @TypeConverter
//    fun toTimestamp(date: Date): String? {
//        val dateTimeFormat = SimpleDateFormat("dd/MM/yyyy")
//        return dateTimeFormat.format(date)
//    }

}