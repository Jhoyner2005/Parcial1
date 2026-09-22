package com.example.parcial1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Logic.CasoLogic
import com.example.parcial1.Screens.CasoScreen
import com.example.parcial1.Screens.FormularioCasoScreen
import com.example.parcial1.Screens.InicioScreen
import com.example.parcial1.ui.theme.Parcial1Theme

class MainActivity : ComponentActivity() {

    private val casoLogic: CasoLogic by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Parcial1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var pantallaActual by remember {
                        mutableStateOf("inicio")
                    }
                    var casoSeleccionado by remember {
                        mutableStateOf<CasoEntity?>(null)
                    }
                    val casos by casoLogic.todosLosCasos.collectAsState()
                    Scaffold(
                        bottomBar = {

                            if (
                                pantallaActual == "inicio" ||
                                pantallaActual == "casos"
                            ) {

                                NavigationBar {
                                    NavigationBarItem(
                                        selected = pantallaActual == "inicio",
                                        onClick = {
                                            pantallaActual = "inicio"
                                        },
                                        icon = {
                                            androidx.compose.material3.Text("⌂")
                                        },
                                        label = {
                                            androidx.compose.material3.Text("Inicio")
                                        }
                                    )

                                    NavigationBarItem(
                                        selected = pantallaActual == "casos",

                                        onClick = {
                                            pantallaActual = "casos"
                                        },

                                        icon = {
                                            androidx.compose.material3.Text("▣")
                                        },

                                        label = {
                                            androidx.compose.material3.Text("Casos")
                                        }
                                    )
                                }
                            }
                        }

                    ) { paddingValues ->

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            when (pantallaActual) {

                                // --------------------------------
                                // INICIO
                                // --------------------------------

                                "inicio" -> {

                                    InicioScreen(
                                        casos = casos,

                                        onVerCasos = {
                                            pantallaActual = "casos"
                                        },

                                        onNuevoCaso = {
                                            pantallaActual = "nuevo"
                                        }
                                    )
                                }
                                "casos" -> {

                                    CasoScreen(
                                        casoLogic = casoLogic,
                                        onEditarCaso = { caso ->
                                            casoSeleccionado = caso
                                            pantallaActual = "editar"
                                        }
                                    )
                                }

                                "nuevo" -> {

                                    FormularioCasoScreen(
                                        caso = null,
                                        onGuardar = {
                                                titulo,
                                                descripcion,
                                                fecha,
                                                estado,
                                                conclusion ->
                                            casoLogic.insertarCaso(
                                                titulo = titulo,
                                                descripcion = descripcion,
                                                fecha = fecha,
                                                estado = estado,
                                                conclusion = conclusion
                                            )
                                            pantallaActual = "casos"
                                        },

                                        onCancelar = {
                                            pantallaActual = "inicio"
                                        }
                                    )
                                }

                                "editar" -> {

                                    FormularioCasoScreen(
                                        caso = casoSeleccionado,
                                        onGuardar = {
                                                titulo,
                                                descripcion,
                                                fecha,
                                                estado,
                                                conclusion ->
                                            if (casoSeleccionado != null) {

                                                val casoActualizado =
                                                    casoSeleccionado!!.copy(
                                                        titulo = titulo,
                                                        descripcion = descripcion,
                                                        fecha = fecha,
                                                        estado = estado,
                                                        conclusion = conclusion
                                                    )

                                                casoLogic.actualizarCaso(
                                                    casoActualizado
                                                )
                                            }

                                            pantallaActual = "casos"
                                            casoSeleccionado = null
                                        },

                                        onCancelar = {
                                            pantallaActual = "casos"
                                            casoSeleccionado = null
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


