package com.example.parcial1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.Database.EntrevistaEntity

// Paleta
private val BackgroundColor = Color(0xFF0F172A)
private val CardBackgroundColor = Color(0xFFFFFFFF)
private val PrimaryText = Color(0xFFEAF2F8)
private val ButtonPrimary = Color(0xFF2563EB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioEntrevistaScreen(
    entrevista: EntrevistaEntity? = null,
    onGuardar: (
        entrevistado: String,
        fecha: String,
        hallazgos: String,
        evidencias: String
    ) -> Unit,
    onCancelar: () -> Unit
) {

    var entrevistado by remember { mutableStateOf(entrevista?.entrevistado ?: "") }
    var fecha by remember { mutableStateOf(entrevista?.fecha ?: "") }
    var hallazgos by remember { mutableStateOf(entrevista?.hallazgos ?: "") }
    var evidencias by remember { mutableStateOf(entrevista?.evidencias ?: "") }

    val focusManager = LocalFocusManager.current

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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor,
                    titleContentColor = PrimaryText,
                    navigationIconContentColor = PrimaryText
                )
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            // ---------- TARJETA CON LOS CAMPOS ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackgroundColor
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        value = entrevistado,
                        onValueChange = { entrevistado = it },
                        label = { Text("Entrevistado") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = hallazgos,
                        onValueChange = { hallazgos = it },
                        label = { Text("Principales hallazgos") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = evidencias,
                        onValueChange = { evidencias = it },
                        label = { Text("Evidencias") },
                        placeholder = { Text("Ej: grabacion, foto, documento...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- BOTON GUARDAR ----------
            Button(
                onClick = {
                    if (entrevistado.isNotBlank()) {
                        onGuardar(
                            entrevistado,
                            fecha,
                            hallazgos,
                            evidencias
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonPrimary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (entrevista == null) "Guardar entrevista"
                    else "Actualizar entrevista",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // ---------- BOTÓN CANCELAR ----------
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryText
                )
            ) {
                Text("Cancelar", fontSize = 15.sp)
            }
        }
    }
}