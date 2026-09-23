package com.example.parcial1.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface EntrevistaDao {
    @Insert
    suspend fun insertarEntrevista(entrevista: EntrevistaEntity)

    @Update
    suspend fun actualizarEntrevista(entrevista: EntrevistaEntity)

    @Delete
    suspend fun eliminarEntrevista(entrevista: EntrevistaEntity)

    @Query("SELECT * FROM entrevistas WHERE casoId = :casoId ORDER BY id DESC")
    fun obtenerEntrevistasPorCaso(casoId: Int): Flow<List<EntrevistaEntity>>

    @Query("SELECT * FROM entrevistas WHERE id = :id")
    suspend fun obtenerEntrevistaPorId(id: Int): EntrevistaEntity?
}