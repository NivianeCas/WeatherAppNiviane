package com.weatherappniviane

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherappniviane.viewmodel.MainViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage(viewModel = viewModel)
        }
        composable("list") {
            ListPage(viewModel = viewModel)
        }
        composable("map") {
            MapPage(viewModel = viewModel)
        }
    }
}
