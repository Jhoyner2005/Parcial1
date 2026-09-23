package com.example.parcial1.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial1.Database.EntrevistaEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEntrevistaScreen(
    entrevista: EntrevistaEntity? = null,
    onGuardar: (
        entrevistado: String,
        fecha: String,
        hallazgos: String
    ) -> Unit,
    onCancelar: () -> Unit
) {

    var entrevistado by remember { mutableStateOf(entrevista?.entrevistado ?: "") }
    var fecha by remember { mutableStateOf(entrevista?.fecha ?: "") }
    var hallazgos by remember { mutableStateOf(entrevista?.hallazgos ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (entrevista == null) "Nueva entrevista"
                        else "Editar entrevista"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = entrevistado,
                onValueChange = { entrevistado = it },
                label = { Text("Entrevistado") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = hallazgos,
                onValueChange = { hallazgos = it },
                label = { Text("Principales hallazgos") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (entrevistado.isNotBlank()) {
                        onGuardar(entrevistado, fecha, hallazgos)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (entrevista == null) "Guardar entrevista"
                    else "Actualizar entrevista"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}