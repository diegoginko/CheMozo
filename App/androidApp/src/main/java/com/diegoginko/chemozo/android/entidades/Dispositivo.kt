package com.diegoginko.chemozo.android.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diegoginko.chemozo.android.utilidades.moshiconverters.Fecha
import com.squareup.moshi.JsonClass
import java.util.GregorianCalendar

@Entity(tableName = "dispositivos")
@JsonClass(generateAdapter = true)
data class Dispositivo(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var nombre : String = "",
    var alias : String = "",
    var activo : Boolean = true,
    @Fecha
    var ultimaConexion : GregorianCalendar?
)
