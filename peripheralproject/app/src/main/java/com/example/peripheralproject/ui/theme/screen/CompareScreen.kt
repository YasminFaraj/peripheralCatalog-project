package com.example.peripheralproject.ui.theme.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peripheralproject.PeripheralsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
    viewModel: PeripheralsViewModel,
    onBack: () -> Unit
) {
    val peripherals by viewModel.peripherals.collectAsState()
    val selectedIds by viewModel.selectedForComparison.collectAsState()

    // Lista dos periféricos selecionados (até 3)
    val selectedPeripherals = peripherals.filter { selectedIds.contains(it.id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparar Periféricos") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Voltar")
                    }
                }
            )
        }
    ) { padding ->
        if (selectedPeripherals.isEmpty()) {
            // Caso o usuário tenha chego aqui sem seleção (ou depois de limpar dados)
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                Text(
                    text = "Nenhum periférico selecionado para comparação.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(padding)
                    .padding(8.dp)
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedPeripherals.forEach { p ->
                    Column(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp)
                    ) {
                        Text(
                            p.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text("Marca: ${p.brand}")
                        Text("Categoria: ${p.category}")
                        Text("Preço: R$ ${p.price}")
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Descrição:",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(p.description ?: "Sem descrição")
                    }
                }
            }
        }
    }
}


