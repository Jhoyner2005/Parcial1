package com.example.parcial1.Logic

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.parcial1.Database.CasoEntity
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
class CasoLogicTest {

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
    fun `insertarCaso debe ejecutarse sin errores en el ViewModel`() = runTest {
        casoLogic.insertarCaso(
            titulo = "Prueba Lógica",
            descripcion = "Testeando ViewModel",
            fecha = "2026-09-22",
            estado = "Cerrado",
            conclusion = "Éxito"
        )
    }
}