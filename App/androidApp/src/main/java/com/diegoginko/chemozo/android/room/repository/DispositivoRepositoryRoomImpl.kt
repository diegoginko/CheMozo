package com.diegoginko.chemozo.android.room.repository

import com.diegoginko.chemozo.android.entidades.Dispositivo
import com.diegoginko.chemozo.android.repository.DispositivoRepository
import com.diegoginko.chemozo.android.room.dao.DispositivoDAO

class DispositivoRepositoryRoomImpl(
    private val dispositivoDAO: DispositivoDAO
): DispositivoRepository {
    override suspend fun insert(dispositivo: Dispositivo): Long {
        return dispositivoDAO.insert(dispositivo)
    }

    override suspend fun update(dispositivo: Dispositivo): Boolean {
        dispositivoDAO.update(dispositivo)
        return true
    }

    override suspend fun delete(dispositivo: Dispositivo): Boolean {
        dispositivoDAO.delete(dispositivo)
        return true
    }

    override suspend fun deleteAll(): Boolean {
        dispositivoDAO.deleteAll()
        return true
    }

    override suspend fun get(id: Long): Dispositivo? {
        return dispositivoDAO.get(id)
    }

    override suspend fun getByNombre(nombre: String): Dispositivo? {
        return dispositivoDAO.getByNombre(nombre)
    }

    override suspend fun getAll(): List<Dispositivo> {
        return dispositivoDAO.getAll()
    }
}