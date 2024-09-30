package com.example.yourhelper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.yourhelper.firebase.ItemPeriferico
import kotlinx.coroutines.launch
import com.example.yourhelper.firebase.PerifericoRepository

@Composable
fun ContentScreen() {
    val gradientColor = listOf(
        Color(0xFFFFFFFF),
        Color(0xFF00BCD4)
    )

    var perifericos by remember { mutableStateOf<List<ItemPeriferico>>(emptyList()) }
    var isModalOpen by remember { mutableStateOf(false) }

    var newNombre by remember { mutableStateOf(TextFieldValue("")) }
    var newConexion by remember { mutableStateOf(TextFieldValue("")) }
    var newType by remember { mutableStateOf(TextFieldValue("")) }
    var newActivo by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val fetchedPerifericos = PerifericoRepository.getAllPerifericos() ?: emptyList()
            perifericos = fetchedPerifericos
        }
    }

    Column(
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

        Button(onClick = { isModalOpen = true }) {
            Text("More")
        }

        if (isModalOpen) {
            AlertDialog(
                onDismissRequest = { isModalOpen = false },
                confirmButton = {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val nuevoPeriferico = ItemPeriferico(
                                    nombre = newNombre.text,
                                    activo = newActivo,
                                    conexion = newConexion.text,
                                    type = newType.text
                                )
                                PerifericoRepository.addPeriferico(nuevoPeriferico)
                                perifericos = perifericos + nuevoPeriferico
                                isModalOpen = false
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    Button(onClick = { isModalOpen = false }) {
                        Text("Cancelar")
                    }
                },
                title = { Text("Agregar Periférico") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newNombre,
                            onValueChange = { newNombre = it },
                            label = { Text("Nombre") }
                        )
                        OutlinedTextField(
                            value = newConexion,
                            onValueChange = { newConexion = it },
                            label = { Text("Conexión") }
                        )
                        OutlinedTextField(
                            value = newType,
                            onValueChange = { newType = it },
                            label = { Text("Tipo") }
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Activo")
                            Switch(
                                checked = newActivo,
                                onCheckedChange = { newActivo = it }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun PerifericoCard(periferico: ItemPeriferico) {
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
                onCheckedChange = { }
            )
        }
    }
}
