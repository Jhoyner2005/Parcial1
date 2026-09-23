package com.example.parcial1.Logic

import android.app.Application
import com.example.parcial1.Database.EntrevistaEntity
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
class EntrevistaLogicTest {

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
    fun `insertarEntrevista guarda la entrevista con los datos correctos`() = runTest {
        val entrevistaCapturada = slot<EntrevistaEntity>()

        coEvery {
            entrevistaRepositoryFalso.insertarEntrevista(capture(entrevistaCapturada))
        } just runs

        casoLogic.insertarEntrevista(
            casoId = 1,
            entrevistado = "Testigo lógico",
            fecha = "230926",
            hallazgos = "Hallazgos desde el test del ViewModel",
            evidencias = "Grabación de audio"
        )

        coVerify { entrevistaRepositoryFalso.insertarEntrevista(any()) }
        assertEquals("Testigo lógico", entrevistaCapturada.captured.entrevistado)
        assertEquals("Grabación de audio", entrevistaCapturada.captured.evidencias)
        assertEquals(1, entrevistaCapturada.captured.casoId)
    }

    @Test
    fun `eliminarEntrevista llama al repositorio con la entrevista correcta`() = runTest {
        val entrevista = EntrevistaEntity(
            id = 1,
            casoId = 1,
            entrevistado = "Testigo",
            fecha = "230926",
            hallazgos = "Hallazgos",
            evidencias = ""
        )

        coEvery { entrevistaRepositoryFalso.eliminarEntrevista(any()) } just runs

        casoLogic.eliminarEntrevista(entrevista)

        coVerify { entrevistaRepositoryFalso.eliminarEntrevista(entrevista) }
    }
}