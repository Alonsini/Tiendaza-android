package com.example.tiendaza.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendaza.data.model.Publicacion
import com.example.tiendaza.ui.viewmodel.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: MainViewModel, onItemClick: (Int) -> Unit) {
    val publicaciones = viewModel.items.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Publicaciones") })
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(publicaciones.value) { item ->
                PublicacionRow(publicaion = item, onClick = { onItemClick(item.id) })
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun PublicacionRow(publicaion: Publicacion, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(publicaion.titulo, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(publicaion.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}