package com.example.tiendaza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tiendaza.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(publicacionId: Long, viewModel: MainViewModel, onBack: () -> Unit) {
    val publicaciones by viewModel.publicaciones.collectAsState()
    val item = publicaciones.find { it.id == publicacionId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (item != null) {
                AsyncImage(
                    model = item.urlImg,
                    contentDescription = item.titulo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(item.titulo, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))

                Text(item.descripcion, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))

                Text(
                    "Precio: $${item.precio}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { /* TODO: Agregar al carrito */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar al Carrito")
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("La publicación no ha sido encontrada")
                }
            }
        }
    }
}