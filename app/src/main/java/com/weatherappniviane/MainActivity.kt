package com.weatherappniviane

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weatherappniviane.model.ui.theme.WeatherAppNivianeTheme
import com.weatherappniviane.viewmodel.MainViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppNivianeTheme {
                // Movi todo o conteúdo para uma Composable function separada
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // Estado da rota atual para controlar visibilidade do FAB
    val currentRoute = navController.currentBackStackEntryAsState()
    val showButton = remember(currentRoute.value) {
        currentRoute.value?.destination?.route == "list"
    }

    // Launcher para solicitação de permissão de localização
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            // Você pode tratar o resultado aqui se necessário
            if (isGranted) {
                // Permissão concedida
            } else {
                // Permissão negada
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bem-vindo/a!") },
                actions = {
                    IconButton(onClick = { /* TODO: Implementar logout */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Sair"
                        )
                    }
                }
            )
        },
        bottomBar = {
            val items = listOf(
                BottomNavItem.HomeButton,
                BottomNavItem.ListButton,
                BottomNavItem.MapButton,
            )
            BottomNavBar(navController = navController, items)
        },
        floatingActionButton = {
            // FAB só aparece na tela de lista
            if (showButton) {
                FloatingActionButton(onClick = {
                    // TODO: Abrir diálogo para adicionar cidade
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Solicita permissão de localização
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED

                if (!hasPermission) {
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            MainNavHost(navController = navController, viewModel = viewModel)
        }
    }
}