package com.example.parcial1.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Database.EntrevistaEntity
import com.example.parcial1.Logic.CasoLogic


private val BackgroundColor = Color(0xFF0F172A)
private val CardBackgroundColor = Color(0xFFFFFFFF)
private val PrimaryText = Color(0xFFEAF2F8)
private val CardPrimaryText = Color(0xFF0F172A)
private val SecondaryText = Color(0xFF475569)
private val ButtonPrimary = Color(0xFF2563EB)

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ---------- TARJETA DEL CASO ----------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardBackgroundColor
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = caso.titulo,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = CardPrimaryText
                        )

                        Spacer(modifier = Modifier.height(8.dp))

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

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Descripción:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CardPrimaryText
                        )

                        Text(
                            text = caso.descripcion,
                            fontSize = 14.sp,
                            color = SecondaryText
                        )

                        if (caso.conclusion.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Conclusión:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CardPrimaryText
                            )

                            Text(
                                text = caso.conclusion,
                                fontSize = 14.sp,
                                color = SecondaryText
                            )
                        }

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
                                Text("Editar caso", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // ---------- TiTULO ENTREVISTAS ----------
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Entrevistas (${entrevistas.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText
                )
            }

            // ---------- BOTON NUEVA ENTREVISTA ----------
            item {
                Button(
                    onClick = onNuevaEntrevista,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Agregar entrevista", fontSize = 14.sp)
                }
            }

            // ---------- LISTA DE ENTREVISTAS ----------
            if (entrevistas.isEmpty()) {
                item {
                    Text(
                        text = "Aún no hay entrevistas registradas.",
                        fontSize = 14.sp,
                        color = PrimaryText.copy(alpha = 0.6f)
                    )
                }
            } else {
                items(entrevistas) { entrevista ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardBackgroundColor
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                text = entrevista.entrevistado,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = CardPrimaryText
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Fecha: ${entrevista.fecha}",
                                fontSize = 13.sp,
                                color = SecondaryText
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Hallazgos:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CardPrimaryText
                            )

                            Text(
                                text = entrevista.hallazgos,
                                fontSize = 14.sp,
                                color = SecondaryText
                            )

                            if (entrevista.evidencias.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Evidencias:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CardPrimaryText
                                )

                                Text(
                                    text = entrevista.evidencias,
                                    fontSize = 14.sp,
                                    color = SecondaryText
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                OutlinedButton(
                                    onClick = { onEditarEntrevista(entrevista) },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = ButtonPrimary
                                    )
                                ) {
                                    Text("Editar", fontSize = 13.sp)
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = { casoLogic.eliminarEntrevista(entrevista) },
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
}