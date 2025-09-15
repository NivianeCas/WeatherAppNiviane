package com.weatherappniviane.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.weatherappniviane.model.City

class MainViewModel : ViewModel() {
    private val _cities = getCities().toMutableStateList()
    val cities
        get() = _cities.toList()

    fun remove(city: City) {
        _cities.remove(city)
    }

    fun add(name: String, location: LatLng? = null) {
        _cities.add(City(name = name, location = location))
    }
}

private fun getCities() = listOf(
    City(name = "Recife", location = LatLng(-8.05, -34.9)),
    City(name = "Caruaru", location = LatLng(-8.27, -35.98)),
    City(name = "João Pessoa", location = LatLng(-7.12, -34.84)),
    City(name = "Cidade sem localização") // Para testar cidades sem coordenadas
)