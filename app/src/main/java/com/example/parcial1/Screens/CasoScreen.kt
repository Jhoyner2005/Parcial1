package com.example.parcial1.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Logic.CasoLogic
//A
@Composable
fun CasoScreen(
    casoLogic: CasoLogic,
    onEditarCaso: (CasoEntity) -> Unit,
    onVerDetalle: (CasoEntity) -> Unit
) {

    val listaCasos by casoLogic.todosLosCasos.collectAsState()

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val casosFiltrados = listaCasos.filter { caso ->

        caso.titulo.contains(
            textoBusqueda,
            ignoreCase = true
        ) ||
                caso.descripcion.contains(
                    textoBusqueda,
                    ignoreCase = true
                ) ||
                caso.estado.contains(
                    textoBusqueda,
                    ignoreCase = true
                )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Gestión de Casos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
            },
            label = {
                Text("Buscar caso")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Casos registrados: ${casosFiltrados.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(casosFiltrados) { caso ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                        .clickable{ onVerDetalle(caso) },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = caso.titulo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "Fecha: ${caso.fecha}"
                        )

                        Text(
                            text = "Estado: ${caso.estado}"
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = caso.descripcion
                        )

                        if (caso.conclusion.isNotBlank()) {

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Conclusión: ${caso.conclusion}"
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            OutlinedButton(
                                onClick = {
                                    onEditarCaso(caso)
                                }
                            ) {
                                Text("Editar")
                            }

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Button(
                                onClick = {
                                    casoLogic.eliminarCaso(caso)
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
