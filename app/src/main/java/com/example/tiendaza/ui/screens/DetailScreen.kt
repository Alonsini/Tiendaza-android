package com.example.tiendaza.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendaza.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(publicacionId: Int, viewModel: MainViewModel, onBack: () -> Unit) {
    val item = viewModel.getPublicacion(publicacionId)

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
                Text(item.titulo, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))
                Text(item.descripcion, style = MaterialTheme.typography.bodyMedium)
            } else {
                Text("La publicacion no ha sido encontrada")
            }
        }
    }
}


