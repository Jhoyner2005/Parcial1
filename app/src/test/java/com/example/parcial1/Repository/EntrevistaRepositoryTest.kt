package com.example.parcial1.Repository

import com.example.parcial1.Database.EntrevistaDao
import com.example.parcial1.Database.EntrevistaEntity
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class EntrevistaRepositoryTest {
    @Test
    fun `insertarEntrevista debe pasar la entrevista al DAO correctamente`() = runBlocking {
        val daoSimulado = mockk<EntrevistaDao>(relaxed = true)
        val repositorio = EntrevistaRepository(daoSimulado)

        val entrevistaDePrueba = EntrevistaEntity(
            casoId = 1,
            entrevistado = "Testigo principal",
            fecha = "2026-09-23",
            hallazgos = "Se encontraron huellas en la escena"
        )

        repositorio.insertarEntrevista(entrevistaDePrueba)

        coVerify(exactly = 1) { daoSimulado.insertarEntrevista(entrevistaDePrueba) }
    }

    @Test
    fun `actualizarEntrevista debe pasar la entrevista al DAO correctamente`() = runBlocking {
        val daoSimulado = mockk<EntrevistaDao>(relaxed = true)
        val repositorio = EntrevistaRepository(daoSimulado)

        val entrevistaDePrueba = EntrevistaEntity(
            id = 1,
            casoId = 1,
            entrevistado = "Testigo actualizado",
            fecha = "2026-09-23",
            hallazgos = "Hallazgos corregidos"
        )

        repositorio.actualizarEntrevista(entrevistaDePrueba)

        coVerify(exactly = 1) { daoSimulado.actualizarEntrevista(entrevistaDePrueba) }
    }

    @Test
    fun `eliminarEntrevista debe pasar la entrevista al DAO correctamente`() = runBlocking {
        val daoSimulado = mockk<EntrevistaDao>(relaxed = true)
        val repositorio = EntrevistaRepository(daoSimulado)

        val entrevistaDePrueba = EntrevistaEntity(
            id = 1,
            casoId = 1,
            entrevistado = "Testigo",
            fecha = "2026-09-23",
            hallazgos = "Hallazgos a eliminar"
        )

        repositorio.eliminarEntrevista(entrevistaDePrueba)

        coVerify(exactly = 1) { daoSimulado.eliminarEntrevista(entrevistaDePrueba) }
    }

    @Test
    fun `obtenerEntrevistasPorCaso debe llamar al DAO con el casoId correcto`() = runBlocking {
        val daoSimulado = mockk<EntrevistaDao>(relaxed = true)
        val repositorio = EntrevistaRepository(daoSimulado)

        repositorio.obtenerEntrevistasPorCaso(5)

        coVerify(exactly = 1) { daoSimulado.obtenerEntrevistasPorCaso(5) }
    }
}