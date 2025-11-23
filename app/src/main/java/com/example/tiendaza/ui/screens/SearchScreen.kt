package com.example.tiendaza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MainViewModel,
    onItemClick: (Long) -> Unit
) {
    val publicaciones = viewModel.items.collectAsState().value
    var txtBusqueda by remember { mutableStateOf("") }

    val publicacionesFiltradas = if (txtBusqueda.isEmpty()) {
        publicaciones
    } else {
        publicaciones.filter {
            it.titulo.lowercase().startsWith(txtBusqueda.lowercase())
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Buscar publicaciones") })

        TextField(
            value = txtBusqueda,
            onValueChange = { txtBusqueda = it },
            label = { Text("Ingresa un nombre para buscar") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(publicacionesFiltradas) { publicacion ->
                PublicacionSearchCard(publicacion = publicacion, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun PublicacionSearchCard(
    publicacion: Publicacion,
    onItemClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://autazaapi.onrender.com/${publicacion.urlImg}",
                contentDescription = publicacion.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(publicacion.titulo, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("$${publicacion.precio}", color = androidx.compose.ui.graphics.Color(0xFF4CAF50))
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = { onItemClick(publicacion.id) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Ver detalles")
            }
        }
    }
}
