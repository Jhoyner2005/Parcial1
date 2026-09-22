package com.example.parcial1.Repository

import com.example.parcial1.Database.CasoDao
import com.example.parcial1.Database.CasoEntity
import kotlinx.coroutines.flow.Flow

class CasoRepository(private val casoDao: CasoDao) {

    val todosLosCasos: Flow<List<CasoEntity>> =
        casoDao.obtenerTodosLosCasos()

    suspend fun insertarCaso(caso: CasoEntity) {
        casoDao.insertarCaso(caso)
    }

    suspend fun actualizarCaso(caso: CasoEntity) {
        casoDao.actualizarCaso(caso)
    }

    suspend fun eliminarCaso(caso: CasoEntity) {
        casoDao.eliminarCaso(caso)
    }

    suspend fun obtenerCasoPorId(id: Int): CasoEntity? {
        return casoDao.obtenerCasoPorId(id)
    }

    fun buscarCasos(texto: String): Flow<List<CasoEntity>> {
        return casoDao.buscarCasos(texto)
    }
}