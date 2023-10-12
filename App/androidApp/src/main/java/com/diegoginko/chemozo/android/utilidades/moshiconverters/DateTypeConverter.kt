package com.diegoginko.chemozo.android.utilidades.moshiconverters

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class Fecha

class DateTypeConverter {

    @SuppressLint("SimpleDateFormat")
    @FromJson
    @Fecha
    fun fromJson(fecha: String): GregorianCalendar? {
        val df: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date: Date? = df.parse(fecha)
        val cal = GregorianCalendar()
        if (date != null)
            cal.time = date
        else
            return null
        return cal
    }

    @ToJson
    fun toJson(@Fecha calendar: GregorianCalendar?): Long?{
        val cal: Calendar? = calendar
        return cal?.timeInMillis
    }

}