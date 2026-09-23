package com.example.parcial1.Repository

import com.example.parcial1.Database.CasoDao
import com.example.parcial1.Database.CasoEntity
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CasoRepositoryTest {

    @Test
    fun `insertarCaso debe pasar el caso al DAO correctamente`() = runBlocking {
        val daoSimulado = mockk<CasoDao>(relaxed = true)
        val repositorio = CasoRepository(daoSimulado)

        val casoDePrueba = CasoEntity(
            titulo = "Prueba Unitaria",
            descripcion = "Verificando el Repositorio",
            fecha = "2026-09-22",
            estado = "Abierto",
            conclusion = "Pendiente"
        )

        repositorio.insertarCaso(casoDePrueba)

        coVerify(exactly = 1) { daoSimulado.insertarCaso(casoDePrueba) }
    }
}