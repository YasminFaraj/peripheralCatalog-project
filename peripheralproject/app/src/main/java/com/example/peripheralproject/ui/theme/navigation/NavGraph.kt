package com.example.peripheralproject.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.peripheralproject.PeripheralsViewModel
import com.example.peripheralproject.ui.theme.screen.DetailScreen
import com.example.peripheralproject.ui.theme.screen.EditPeripheralScreen
import com.example.peripheralproject.ui.theme.screen.FavoritesScreen
import com.example.peripheralproject.ui.theme.screen.HomeScreen
import com.example.peripheralproject.ui.theme.screen.CompareScreen

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val FAVORITES = "favorites"
    const val EDIT = "edit"
    const val COMPARE = "compare"
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    viewModel: PeripheralsViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onOpenDetail = { id ->
                    navController.navigate("${Routes.DETAIL}/$id")
                },
                onOpenFavorites = {
                    navController.navigate(Routes.FAVORITES)
                },
                onOpenCreate = {
                    navController.navigate(Routes.EDIT)
                },
                onOpenCompare = {
                    navController.navigate(Routes.COMPARE)
                }
            )
        }

        composable(
            route = "${Routes.DETAIL}/{peripheralId}",
            arguments = listOf(
                navArgument("peripheralId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("peripheralId") ?: 0
            DetailScreen(
                viewModel = viewModel,
                peripheralId = id,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("${Routes.EDIT}/$id") }
            )
        }

        composable(Routes.FAVORITES) {
            FavoritesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onOpenDetail = { id ->
                    navController.navigate("${Routes.DETAIL}/$id")
                }
            )
        }

        composable(
            route = Routes.EDIT
        ) {
            EditPeripheralScreen(
                viewModel = viewModel,
                peripheralId = null,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "${Routes.EDIT}/{peripheralId}",
            arguments = listOf(
                navArgument("peripheralId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("peripheralId")
            EditPeripheralScreen(
                viewModel = viewModel,
                peripheralId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.COMPARE) {
            CompareScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

