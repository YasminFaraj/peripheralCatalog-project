package com.example.peripheralproject.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.peripheralproject.PeripheralsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPeripheralScreen(
    viewModel: PeripheralsViewModel,
    peripheralId: Int?,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    // Estados do formulário
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Se for edição, carregar dados
    LaunchedEffect(peripheralId) {
        if (peripheralId != null) {
            viewModel.selectPeripheral(peripheralId)
        }
    }

    val selected by viewModel.selectedPeripheral.collectAsState()

    LaunchedEffect(selected) {
        if (peripheralId != null && selected != null) {
            name = selected!!.name
            brand = selected!!.brand
            category = selected!!.category
            priceText = selected!!.price.toString()
            description = selected!!.description ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (peripheralId == null) "Novo Periférico" else "Editar Periférico") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = priceText,
                onValueChange = { priceText = it },
                label = { Text("Preço") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val price = priceText.toDoubleOrNull() ?: 0.0
                    scope.launch {
                        viewModel.savePeripheral(
                            existingId = peripheralId,
                            name = name,
                            brand = brand,
                            category = category,
                            price = price,
                            description = description
                        )
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
