package com.diegoginko.chemozo.android.repository

import com.diegoginko.chemozo.android.entidades.Dispositivo

interface DispositivoRepository {

    suspend fun insert(dispositivo: Dispositivo):Long
    suspend fun update(dispositivo: Dispositivo):Boolean
    suspend fun delete(dispositivo: Dispositivo):Boolean
    suspend fun deleteAll():Boolean
    suspend fun get(id:Long):Dispositivo?
    suspend fun getByNombre(nombre:String):Dispositivo?
    suspend fun getAll():List<Dispositivo>
}