package com.diegoginko.chemozo.android.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.diegoginko.chemozo.android.entidades.Dispositivo
import java.util.GregorianCalendar

@Dao
interface DispositivoDAO {
    @Insert
    fun insert(dispositivo: Dispositivo):Long

    @Update
    fun update(dispositivo: Dispositivo)

    @Delete
    fun delete(dispositivo: Dispositivo)

    @Query("DELETE FROM dispositivos")
    fun deleteAll()

    @Query("SELECT * FROM dispositivos WHERE id=:id LIMIT 1")
    fun get(id:Long):Dispositivo?

    @Query("SELECT * FROM dispositivos WHERE nombre=:nombre LIMIT 1")
    fun getByNombre(nombre:String):Dispositivo?

    @Query("SELECT * FROM dispositivos")
    fun getAll(): List<Dispositivo>

    @Query("UPDATE dispositivos SET ultimaConexion =:ultimaConexion WHERE nombre =:nombre")
    fun updateConexion(nombre:String, ultimaConexion:GregorianCalendar)

}