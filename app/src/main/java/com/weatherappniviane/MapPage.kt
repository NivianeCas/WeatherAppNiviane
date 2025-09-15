package com.weatherappniviane

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.weatherappniviane.viewmodel.MainViewModel

@Composable
fun MapPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val recife = LatLng(-8.05, -34.9)
    val caruaru = LatLng(-8.27, -35.98)
    val joaopessoa = LatLng(-7.12, -34.84)

    val cameraPositionState = rememberCameraPositionState()

    val context = LocalContext.current
    val hasLocationPermission = remember {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = hasLocationPermission // Mostrar localização do usuário
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true // Botão de localização
            ),
            onMapClick = { latLng ->
                // Adiciona nova cidade no local clicado (conforme PDF - Passo 5)
                viewModel.add("Cidade@${latLng.latitude}:${latLng.longitude}", latLng)
            }
        ) {
            //  Passo 3)
            Marker(
                state = MarkerState(position = recife),
                title = "Recife",
                snippet = "Marcador em Recife",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
            )
            Marker(
                state = MarkerState(position = caruaru),
                title = "Caruaru",
                snippet = "Marcador em Caruaru",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            )
            Marker(
                state = MarkerState(position = joaopessoa),
                title = "João Pessoa",
                snippet = "Marcador em João Pessoa",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )

            // Passo 5)
            viewModel.cities.forEach { city ->
                if (city.location != null) {
                    Marker(
                        state = MarkerState(position = city.location),
                        title = city.name,
                        snippet = "${city.location.latitude}:${city.location.longitude}"
                    )
                }
            }
        }
    }
}