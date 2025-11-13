package com.example.peripheralproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.peripheralproject.ui.theme.PeripheralProjectTheme
import com.example.peripheralproject.ui.theme.navigation.AppNavGraph

@Composable
fun PeriCatalogApp(
    viewModel: PeripheralsViewModel
) {
    PeripheralProjectTheme {
        AppNavGraph(
            modifier = Modifier,
            viewModel = viewModel
        )
    }
}
