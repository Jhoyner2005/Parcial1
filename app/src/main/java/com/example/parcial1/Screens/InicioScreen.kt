package com.example.parcial1.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcial1.Database.CasoEntity
//A
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
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Gestión de Casos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Resumen general",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Total de casos: $totalCasos")
                Text("En investigación: $casosInvestigacion")
                Text("Cerrados: $casosCerrados")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onVerCasos,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver casos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onNuevoCaso,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo caso")
        }
    }
}