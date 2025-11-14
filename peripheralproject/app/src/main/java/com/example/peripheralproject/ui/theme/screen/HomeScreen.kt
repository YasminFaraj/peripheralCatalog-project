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
    onOpenCreate: () -> Unit,
    onOpenCompare: () -> Unit
) {
    val peripherals by viewModel.peripherals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val selectedForComparison by viewModel.selectedForComparison.collectAsState()

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

            // Botão simples para abrir a tela de comparação
            Button(
                onClick = onOpenCompare,
                enabled = selectedForComparison.size in 2..3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text("Comparar (${selectedForComparison.size}/3)")
            }

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
                            isSelected = selectedForComparison.contains(item.id),
                            onToggleCompare = { viewModel.toggleCompareSelection(item.id) },
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
    isSelected: Boolean,
    onToggleCompare: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleCompare() }
            )
            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClick)
            ) {
                Text(peripheral.name, style = MaterialTheme.typography.titleMedium)
                Text(peripheral.brand, style = MaterialTheme.typography.bodyMedium)
                Text("R$ ${peripheral.price}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
