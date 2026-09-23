package com.example.parcial1.Logic

import android.app.Application
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Repository.CasoRepository
import com.example.parcial1.Repository.EntrevistaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CasoLogicTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var applicationFalsa: Application
    private lateinit var casoRepositoryFalso: CasoRepository
    private lateinit var entrevistaRepositoryFalso: EntrevistaRepository
    private lateinit var casoLogic: CasoLogic

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        applicationFalsa = mockk(relaxed = true)
        casoRepositoryFalso = mockk(relaxed = true)
        entrevistaRepositoryFalso = mockk(relaxed = true)

        // El StateFlow de todosLosCasos necesita este mock para no fallar
        coEvery { casoRepositoryFalso.todosLosCasos } returns flowOf(emptyList())

        casoLogic = CasoLogic(
            application = applicationFalsa,
            repository = casoRepositoryFalso,
            entrevistaRepository = entrevistaRepositoryFalso
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insertarCaso guarda el caso con los datos correctos`() = runTest {
        val casoCapturado = slot<CasoEntity>()

        coEvery {
            casoRepositoryFalso.insertarCaso(capture(casoCapturado))
        } just runs

        casoLogic.insertarCaso(
            titulo = "Prueba Lógica",
            descripcion = "Testeando ViewModel",
            fecha = "220926",
            estado = "Cerrado",
            conclusion = "Éxito"
        )

        coVerify { casoRepositoryFalso.insertarCaso(any()) }
        assertEquals("Prueba Lógica", casoCapturado.captured.titulo)
        assertEquals("Cerrado", casoCapturado.captured.estado)
        assertEquals("Éxito", casoCapturado.captured.conclusion)
    }

    @Test
    fun `actualizarCaso llama al repositorio con el caso correcto`() = runTest {
        val caso = CasoEntity(
            id = 1,
            titulo = "Caso actualizado",
            descripcion = "Nueva descripción",
            fecha = "230926",
            estado = "Publicado",
            conclusion = "Conclusión actualizada"
        )

        coEvery { casoRepositoryFalso.actualizarCaso(any()) } just runs

        casoLogic.actualizarCaso(caso)

        coVerify { casoRepositoryFalso.actualizarCaso(caso) }
    }

    @Test
    fun `eliminarCaso llama al repositorio con el caso correcto`() = runTest {
        val caso = CasoEntity(
            id = 5,
            titulo = "A eliminar",
            descripcion = "",
            fecha = "010126",
            estado = "Cerrado",
            conclusion = ""
        )

        coEvery { casoRepositoryFalso.eliminarCaso(any()) } just runs

        casoLogic.eliminarCaso(caso)

        coVerify { casoRepositoryFalso.eliminarCaso(caso) }
    }
}