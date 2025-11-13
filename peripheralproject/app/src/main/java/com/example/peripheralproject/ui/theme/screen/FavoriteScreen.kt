package com.example.peripheralproject.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peripheralproject.PeripheralsViewModel
import com.example.peripheralproject.data.model.PeripheralEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: PeripheralsViewModel,
    onBack: () -> Unit,
    onOpenDetail: (Int) -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(favorites) { item ->
                FavoriteItem(
                    peripheral = item,
                    onClick = { onOpenDetail(item.id) }
                )
            }
        }
    }
}

@Composable
private fun FavoriteItem(
    peripheral: PeripheralEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(peripheral.name, style = MaterialTheme.typography.titleMedium)
            Text(peripheral.brand, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
