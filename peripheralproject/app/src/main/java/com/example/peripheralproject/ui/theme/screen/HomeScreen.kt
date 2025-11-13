package com.example.peripheralproject.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peripheralproject.PeripheralsViewModel
import com.example.peripheralproject.data.model.PeripheralEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PeripheralsViewModel,
    onOpenDetail: (Int) -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenCreate: () -> Unit
) {
    val peripherals by viewModel.peripherals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Periféricos") },
                actions = {
                    IconButton(onClick = onOpenFavorites) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onOpenCreate) {
                Icon(Icons.Default.Add, contentDescription = "Novo periférico")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.updateSearch(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                label = { Text("Buscar por nome") }
            )

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(peripherals) { item ->
                        PeripheralItem(
                            peripheral = item,
                            onClick = { onOpenDetail(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PeripheralItem(
    peripheral: PeripheralEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(peripheral.name, style = MaterialTheme.typography.titleMedium)
            Text(peripheral.brand, style = MaterialTheme.typography.bodyMedium)
            Text("R$ ${peripheral.price}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
