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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.Database.CasoEntity
import kotlinx.coroutines.launch



private val BackgroundColor = Color(0xFF0F172A)
private val CardBackgroundColor = Color(0xFFFFFFFF)
private val PrimaryText = Color(0xFFEAF2F8)
private val CardPrimaryText = Color(0xFF0F172A)
private val SecondaryText = Color(0xFF475569)
private val ButtonPrimary = Color(0xFF2563EB)
private val InputBackground = Color(0xFF1E293B)

// Enum de estado
enum class EstadoCaso(val label: String) {
    EN_INVESTIGACION("En investigación"),
    PUBLICADO("Publicado"),
    CERRADO("Cerrado"),
    EN_EDICION("En edición");

    companion object {
        fun fromLabel(label: String): EstadoCaso =
            entries.find { it.label == label } ?: EN_INVESTIGACION
    }
}

class FechaVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val numeros = text.text
        val fecha = when {
            numeros.length <= 2 -> numeros
            numeros.length <= 4 -> "${numeros.substring(0, 2)}/${numeros.substring(2)}"
            else -> "${numeros.substring(0, 2)}/${numeros.substring(2, 4)}/${numeros.substring(4)}"
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = when {
                offset <= 2 -> offset
                offset <= 4 -> offset + 1
                else -> offset + 2
            }
            override fun transformedToOriginal(offset: Int): Int = when {
                offset <= 2 -> offset
                offset <= 5 -> offset - 1
                else -> offset - 2
            }
        }
        return TransformedText(AnnotatedString(fecha), offsetMapping)
    }
}

fun formatearFecha(fecha: String): String {
    val numeros = fecha.filter { it.isDigit() }.take(6)
    return when {
        numeros.length <= 2 -> numeros
        numeros.length <= 4 -> "${numeros.substring(0, 2)}/${numeros.substring(2)}"
        else -> "${numeros.substring(0, 2)}/${numeros.substring(2, 4)}/${numeros.substring(4)}"
    }
}

fun fechaValida(fecha: String): Boolean {
    if (fecha.length != 6) return false
    val dia = fecha.substring(0, 2).toInt()
    val mes = fecha.substring(2, 4).toInt()
    return dia in 1..31 && mes in 1..12
}

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
    var conclusion by remember { mutableStateOf(caso?.conclusion ?: "") }

    var estado by remember {
        mutableStateOf(
            caso?.estado?.let { EstadoCaso.fromLabel(it) } ?: EstadoCaso.EN_INVESTIGACION
        )
    }

    var estadoExpandido by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (caso == null) "Nuevo caso" else "Editar caso")
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título del caso") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { nuevoTexto ->
                            fecha = nuevoTexto.filter { it.isDigit() }.take(6)
                        },
                        label = { Text("Fecha") },
                        placeholder = { Text("DD/MM/AA") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        visualTransformation = FechaVisualTransformation()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // ---------- DROPDOWN DE ESTADO ----------
                    ExposedDropdownMenuBox(
                        expanded = estadoExpandido,
                        onExpandedChange = { estadoExpandido = !estadoExpandido }
                    ) {
                        OutlinedTextField(
                            value = estado.label,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Estado") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = estadoExpandido
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = estadoExpandido,
                            onDismissRequest = { estadoExpandido = false }
                        ) {
                            EstadoCaso.entries.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion.label) },
                                    onClick = {
                                        estado = opcion
                                        estadoExpandido = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = conclusion,
                        onValueChange = { conclusion = it },
                        label = { Text("Conclusión") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------- BOTÓN GUARDAR ----------
            Button(
                onClick = {
                    if (titulo.isNotBlank() && fechaValida(fecha)) {

                        if (estado == EstadoCaso.CERRADO && conclusion.isBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "No puedes cerrar un caso sin una conclusión"
                                )
                            }
                            return@Button
                        }

                        onGuardar(
                            titulo,
                            descripcion,
                            fecha,
                            estado.label,
                            conclusion
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
                    text = if (caso == null) "Guardar caso" else "Actualizar caso",
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