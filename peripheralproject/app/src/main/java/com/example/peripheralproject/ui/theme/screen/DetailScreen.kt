package com.example.peripheralproject.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peripheralproject.PeripheralsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: PeripheralsViewModel,
    peripheralId: Int,
    onBack: () -> Unit,
    onEdit: () -> Unit
) {
    val peripheral by viewModel.selectedPeripheral.collectAsState()
    val isFavorite by viewModel.isFavorite(peripheralId).collectAsState(initial = false)

    LaunchedEffect(peripheralId) {
        viewModel.selectPeripheral(peripheralId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Periférico") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            peripheral?.let { p ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(p.name, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(4.dp))
                    Text("Marca: ${p.brand}")
                    Text("Categoria: ${p.category}")
                    Text("Preço: R$ ${p.price}")
                    Spacer(Modifier.height(8.dp))
                    Text("Descrição:", style = MaterialTheme.typography.titleMedium)
                    Text(p.description ?: "Sem descrição")

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.toggleFavorite(peripheralId, isFavorite)
                        }
                    ) {
                        Text(
                            if (isFavorite) "Remover dos favoritos"
                            else "Adicionar aos favoritos"
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = onEdit,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Editar")
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.deletePeripheral(peripheralId); onBack() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Excluir")
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Periférico não encontrado.")
                }
            }
        }
    }
}
