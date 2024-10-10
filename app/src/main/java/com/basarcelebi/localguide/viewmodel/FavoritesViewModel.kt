package com.basarcelebi.localguide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.basarcelebi.localguide.data.Place
import com.basarcelebi.localguide.model.PlaceObject
import com.basarcelebi.localguide.repositories.PlaceRepository

class FavoritesViewModel : ViewModel() {
    // LiveData ile places'i saklamak için MutableLiveData kullanıyoruz
    private val _places = MutableLiveData<MutableList<Place>?>()
    val places: MutableLiveData<MutableList<Place>?> get() = _places

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        PlaceRepository().getPlaces { loadedPlaces ->
            _places.value = loadedPlaces.toMutableList()
        }
    }

    private val _favoritePlaces = MediatorLiveData<List<Place>>()
    val favoritePlaces: LiveData<List<Place>> get() = _favoritePlaces

    init {
        _favoritePlaces.addSource(_places) { places ->
            if (places != null) {
                _favoritePlaces.value = places.filter { it.isFavorited }
            }
        }
    }

    fun toggleFavorite(place: Place) {
        val updatedPlaces = _places.value?.map {
            if (it.place_id == place.place_id) it.copy(isFavorited = !it.isFavorited) else it
        }?.toMutableList()

        _places.value = updatedPlaces // LiveData'yı güncelle

        // Veriyi Firestore'da da güncelle
        PlaceRepository().updatePlace(place.copy(isFavorited = !place.isFavorited))
    }

}







