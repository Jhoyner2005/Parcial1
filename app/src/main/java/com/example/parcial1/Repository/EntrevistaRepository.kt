package com.example.parcial1.Repository

import com.example.parcial1.Database.EntrevistaDao
import com.example.parcial1.Database.EntrevistaEntity
import kotlinx.coroutines.flow.Flow


class EntrevistaRepository(private val entrevistaDao: EntrevistaDao) {
    fun obtenerEntrevistasPorCaso(casoId: Int): Flow<List<EntrevistaEntity>> {
        return entrevistaDao.obtenerEntrevistasPorCaso(casoId)
    }

    suspend fun insertarEntrevista(entrevista: EntrevistaEntity) {
        entrevistaDao.insertarEntrevista(entrevista)
    }

    suspend fun actualizarEntrevista(entrevista: EntrevistaEntity) {
        entrevistaDao.actualizarEntrevista(entrevista)
    }

    suspend fun eliminarEntrevista(entrevista: EntrevistaEntity) {
        entrevistaDao.eliminarEntrevista(entrevista)
    }

    suspend fun obtenerEntrevistaPorId(id: Int): EntrevistaEntity? {
        return entrevistaDao.obtenerEntrevistaPorId(id)
    }

}