package com.example.parcial1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.Database.CasoEntity

// Paleta de colores
private val BackgroundColor = Color(0xFF0F172A)      // Fondo azul noche oscuro
private val CardBackgroundColor = Color(0xFFFFFFFF)  // Fondo blanco para la tarjeta
private val PrimaryText = Color(0xFFEAF2F8)          // Texto claro (para título fuera de la tarjeta)
private val CardPrimaryText = Color(0xFF0F172A)      // Texto oscuro (para títulos DENTRO de la tarjeta)
private val SecondaryText = Color(0xFF475569)        // Gris oscuro para detalles dentro de la tarjeta
private val AccentInvestigacion = Color(0xFFD97706)  // Naranja
private val AccentCerrado = Color(0xFF059669)        // Verde
private val ButtonPrimary = Color(0xFF2563EB)        // Azul vibrante para botón principal

@Composable
fun InicioScreen(
    casos: List<CasoEntity>,
    onVerCasos: () -> Unit,
    onNuevoCaso: () -> Unit
) {
    val totalCasos = casos.size

    val casosInvestigacion = casos.count {
        it.estado.equals("En investigación", ignoreCase = true)
    }

    val casosCerrados = casos.count {
        it.estado.equals("Cerrado", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Título principal fuera de la tarjeta
        Text(
            text = "Gestión de Casos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta blanca
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackgroundColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Resumen general",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CardPrimaryText
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Fila: Total de Casos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total de casos:",
                        fontSize = 15.sp,
                        color = SecondaryText
                    )
                    Text(
                        text = "$totalCasos",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = CardPrimaryText
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Fila: En Investigación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "En investigación:",
                        fontSize = 15.sp,
                        color = SecondaryText
                    )
                    Text(
                        text = "$casosInvestigacion",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentInvestigacion
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Fila: Cerrados
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cerrados:",
                        fontSize = 15.sp,
                        color = SecondaryText
                    )
                    Text(
                        text = "$casosCerrados",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentCerrado
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Boton Principal
        Button(
            onClick = onVerCasos,
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
                text = "Ver casos",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Boton Secundario
        OutlinedButton(
            onClick = onNuevoCaso,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = PrimaryText
            )
        ) {
            Text(
                text = "Nuevo caso",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}