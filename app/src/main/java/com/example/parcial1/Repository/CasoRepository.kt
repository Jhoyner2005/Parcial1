package com.example.parcial1.Repository
import com.example.parcial1.Database.CasoDao
import com.example.parcial1.Database.CasoEntity
import kotlinx.coroutines.flow.Flow

class CasoRepository(private val casoDao: CasoDao) {

    val todosLosCasos: Flow<List<CasoEntity>> = casoDao.obtenerTodosLosCasos()

    suspend fun insertarCaso(caso: CasoEntity) {
        casoDao.insertarCaso(caso)
    }
}