package com.example.parcial1.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CasoDao {

    @Insert
    suspend fun insertarCaso(caso: CasoEntity)

    @Update
    suspend fun actualizarCaso(caso: CasoEntity)

    @Delete
    suspend fun eliminarCaso(caso: CasoEntity)

    @Query("SELECT * FROM casos ORDER BY id DESC")
    fun obtenerTodosLosCasos(): Flow<List<CasoEntity>>

    @Query("SELECT * FROM casos WHERE id = :id")
    suspend fun obtenerCasoPorId(id: Int): CasoEntity?

    @Query("""
        SELECT * FROM casos
        WHERE titulo LIKE '%' || :texto || '%'
        OR descripcion LIKE '%' || :texto || '%'
        OR estado LIKE '%' || :texto || '%'
        ORDER BY id DESC
    """)
    fun buscarCasos(texto: String): Flow<List<CasoEntity>>
}