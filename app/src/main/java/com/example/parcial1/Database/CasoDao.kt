package com.example.parcial1.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CasoDao {

    @Insert
    suspend fun insertarCaso(caso: CasoEntity)

    @Query("SELECT * FROM casos ORDER BY id DESC")
    fun obtenerTodosLosCasos(): Flow<List<CasoEntity>>
}