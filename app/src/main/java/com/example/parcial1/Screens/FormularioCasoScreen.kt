package com.example.parcial1.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial1.Database.CasoEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioCasoScreen(
    caso: CasoEntity? = null,
    onGuardar: (
        titulo: String,
        descripcion: String,
        fecha: String,
        estado: String,
        conclusion: String
    ) -> Unit,
    onCancelar: () -> Unit
) {

    var titulo by remember { mutableStateOf(caso?.titulo ?: "") }
    var descripcion by remember { mutableStateOf(caso?.descripcion ?: "") }
    var fecha by remember { mutableStateOf(caso?.fecha ?: "") }
    var estado by remember { mutableStateOf(caso?.estado ?: "") }
    var conclusion by remember { mutableStateOf(caso?.conclusion ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (caso == null) "Nuevo caso"
                        else "Editar caso"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onCancelar
                    ) {
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
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título del caso") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
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
                value = estado,
                onValueChange = { estado = it },
                label = { Text("Estado") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = conclusion,
                onValueChange = { conclusion = it },
                label = { Text("Conclusión") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        onGuardar(
                            titulo,
                            descripcion,
                            fecha,
                            estado,
                            conclusion
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (caso == null) {
                        "Guardar caso"
                    } else {
                        "Actualizar caso"
                    }
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
//A