package com.example.parcial1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Logic.CasoLogic


private val BackgroundColor = Color(0xFF0F172A)
private val CardBackgroundColor = Color(0xFFFFFFFF)
private val PrimaryText = Color(0xFFEAF2F8)
private val CardPrimaryText = Color(0xFF0F172A)
private val SecondaryText = Color(0xFF475569)
private val ButtonPrimary = Color(0xFF2563EB)
private val SearchBackground = Color(0xFF1E293B)

@Composable
fun CasoScreen(
    casoLogic: CasoLogic,
    onEditarCaso: (CasoEntity) -> Unit,
    onVerDetalle: (CasoEntity) -> Unit
) {

    val listaCasos by casoLogic.todosLosCasos.collectAsState()

    var textoBusqueda by remember { mutableStateOf("") }

    val casosFiltrados = listaCasos.filter { caso ->
        caso.titulo.contains(textoBusqueda, ignoreCase = true) ||
                caso.descripcion.contains(textoBusqueda, ignoreCase = true) ||
                caso.estado.contains(textoBusqueda, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp)
    ) {

        Text(
            text = "Mis casos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            placeholder = {
                Text(
                    "Buscar caso...",
                    color = PrimaryText.copy(alpha = 0.5f)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = SearchBackground,
                unfocusedContainerColor = SearchBackground,
                focusedBorderColor = ButtonPrimary,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = PrimaryText,
                unfocusedTextColor = PrimaryText,
                cursorColor = ButtonPrimary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Casos registrados: ${casosFiltrados.size}",
            fontSize = 14.sp,
            color = PrimaryText.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(casosFiltrados) { caso ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onVerDetalle(caso) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardBackgroundColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = caso.titulo,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = CardPrimaryText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Fecha: ${caso.fecha}",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )

                        Text(
                            text = "Estado: ${caso.estado}",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = caso.descripcion,
                            fontSize = 14.sp,
                            color = SecondaryText,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onEditarCaso(caso) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = ButtonPrimary
                                )
                            ) {
                                Text("Editar", fontSize = 13.sp)
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = { casoLogic.eliminarCaso(caso) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFDC2626),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Eliminar", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}