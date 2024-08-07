package com.basarcelebi.localguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.basarcelebi.localguide.data.Place
import com.basarcelebi.localguide.model.PlaceObject

class FavoritesViewModel : ViewModel() {
    private val _places = MutableLiveData(PlaceObject.getPlaces())
    val places: MutableLiveData<MutableList<Place>> get() = _places

    private val _favoritePlaces = MediatorLiveData<List<Place>>()
    val favoritePlaces: LiveData<List<Place>> get() = _favoritePlaces

    init {
        _favoritePlaces.addSource(_places) { places ->
            _favoritePlaces.value = places.filter { it.isFavorited }
        }
    }

    fun toggleFavorite(place: Place) {
        val updatedPlaces = _places.value?.map {
            if (it.name == place.name) it.copy(isFavorited = !it.isFavorited) else it
        }?.toMutableList()
        _places.value = updatedPlaces
        PlaceObject.getPlaces().clear()
        PlaceObject.getPlaces().addAll(updatedPlaces ?: emptyList())
    }
}







