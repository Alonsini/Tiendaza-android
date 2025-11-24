package com.example.tiendaza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: MainViewModel, onItemClick: (Long) -> Unit) {
    val publicaciones by viewModel.publicaciones.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var txtBusqueda by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Buscar publicaciones") })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = txtBusqueda,
                onValueChange = { txtBusqueda = it },
                label = { Text("Ingresa un nombre para buscar") },
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                viewModel.searchPublicaciones(txtBusqueda)
            }) {
                Text("🔍")
            }
        }

        Spacer(Modifier.height(8.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            publicaciones.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron resultados")
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(publicaciones) { publicacion ->
                        PublicacionSearchCard(publicacion, onItemClick)
                    }
                }
            }
        }
    }
}

@Composable
fun PublicacionSearchCard(publicacion: Publicacion, onItemClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = publicacion.urlImg,
                contentDescription = publicacion.titulo,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
            Text(publicacion.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("$${publicacion.precio}", color = Color(0xFF4CAF50), fontSize = 14.sp)
            Spacer(Modifier.height(4.dp))

            Button(
                onClick = { onItemClick(publicacion.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver detalles")
            }
        }
    }
}

