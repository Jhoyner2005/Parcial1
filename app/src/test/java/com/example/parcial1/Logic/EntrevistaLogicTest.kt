package com.example.parcial1.Logic

import android.app.Application
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class EntrevistaLogicTest {
    @get:Rule
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var applicationFalsa: Application
    private lateinit var casoLogic: CasoLogic

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        applicationFalsa = mockk<Application>(relaxed = true)
        casoLogic = CasoLogic(applicationFalsa)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insertarEntrevista debe ejecutarse sin errores en el ViewModel`() = runTest {
        casoLogic.insertarEntrevista(
            casoId = 1,
            entrevistado = "Testigo lógico",
            fecha = "2026-09-23",
            hallazgos = "Hallazgos desde el test del ViewModel"
        )
    }

    @Test
    fun `eliminarEntrevista debe ejecutarse sin errores en el ViewModel`() = runTest {
        val entrevista = com.example.parcial1.Database.EntrevistaEntity(
            id = 1,
            casoId = 1,
            entrevistado = "Testigo",
            fecha = "2026-09-23",
            hallazgos = "Hallazgos"
        )

        casoLogic.eliminarEntrevista(entrevista)
    }
}