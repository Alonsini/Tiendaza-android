package com.example.tiendaza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
fun HomeScreen(
    viewModel: MainViewModel,
    onItemClick: (Long) -> Unit
) {
    val publicaciones = viewModel.items.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Publicaciones") })

        if (publicaciones.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(publicaciones) { publicacion ->
                    PublicacionCard(publicacion = publicacion, onItemClick = onItemClick)
                }
            }
        }
    }
}

@Composable
fun PublicacionCard(
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
            Text(publicacion.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("$${publicacion.precio}", color = Color(0xFF4CAF50), fontSize = 14.sp)
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
