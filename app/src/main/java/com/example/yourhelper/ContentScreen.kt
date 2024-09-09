package com.example.yourhelper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Switch



data class Periferico(
    val nombre: String,
    val activo: Boolean,
    val conexion: String,
    val type : String
);

fun Periferico.toggleActivo(): Periferico {
    return this.copy(activo = !this.activo)
}


val perifericos = listOf(
    Periferico("Sensor de gas", true, "bluetooth", "Casa"),
    Periferico("Sensor de movimiento", false, "bluetooth", "Casa"),
    Periferico("Aviso llamadas", false, "bluetooth", "Personal"),
    Periferico("Ampolleta", true, "bluetooth", "Casa")
)


@Composable
fun ContentScreen(){
    val gradientColor = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )

    val context = LocalContext.current;

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColor)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(perifericos) { periferico ->
                PerifericoCard(periferico)
            }
        }
    }
}

@Composable
fun PerifericoCard(periferico: Periferico) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = periferico.nombre, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (periferico.activo) "Conectado" else "Desconectado",
                style = MaterialTheme.typography.bodyMedium,
                color = if (periferico.activo) Color.Green else Color.Red
            )
            Switch(
                checked = periferico.activo,
                onCheckedChange = { checked ->
                    periferico.toggleActivo();
                }
            )

        }
    }
}