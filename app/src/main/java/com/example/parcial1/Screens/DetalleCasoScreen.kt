package com.example.parcial1.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Database.EntrevistaEntity
import com.example.parcial1.Logic.CasoLogic


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleCasoScreen(
    caso: CasoEntity,
    casoLogic: CasoLogic,
    onVolver: () -> Unit,
    onEditarCaso: (CasoEntity) -> Unit,
    onNuevaEntrevista: () -> Unit,
    onEditarEntrevista: (EntrevistaEntity) -> Unit
) {

    val entrevistas by casoLogic
        .obtenerEntrevistasPorCaso(caso.id)
        .collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del caso") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // ---------- TARJETA DEL CASO ----------
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = caso.titulo,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Fecha: ${caso.fecha}")
                        Text(text = "Estado: ${caso.estado}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Descripción:")
                        Text(text = caso.descripcion)

                        if (caso.conclusion.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Conclusión:")
                            Text(text = caso.conclusion)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onEditarCaso(caso) }
                            ) {
                                Text("Editar caso")
                            }
                        }
                    }
                }
            }

            // ---------- TÍTULO ENTREVISTAS ----------
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Entrevistas (${entrevistas.size})",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // ---------- BOTÓN NUEVA ENTREVISTA ----------
            item {
                Button(
                    onClick = onNuevaEntrevista,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar entrevista")
                }
            }

            // ---------- LISTA DE ENTREVISTAS ----------
            if (entrevistas.isEmpty()) {
                item {
                    Text(
                        text = "Aún no hay entrevistas registradas.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                items(entrevistas) { entrevista ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = entrevista.entrevistado,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(text = "Fecha: ${entrevista.fecha}")

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(text = "Hallazgos:")
                            Text(text = entrevista.hallazgos)

                            if (entrevista.evidencias.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = "Evidencias:")
                                Text(
                                    text = entrevista.evidencias,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))



                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {

                                OutlinedButton(
                                    onClick = { onEditarEntrevista(entrevista) }
                                ) {
                                    Text("Editar")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = {
                                        casoLogic.eliminarEntrevista(entrevista)
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
}