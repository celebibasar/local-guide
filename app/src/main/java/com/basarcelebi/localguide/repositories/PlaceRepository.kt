package com.basarcelebi.localguide.repositories

import android.util.Log
import com.basarcelebi.localguide.data.Place
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import java.util.UUID

class PlaceRepository {
    val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    fun addPlace(place: Place) {
        val placeId = UUID.randomUUID().toString()
        db.collection("places")
            .document(placeId)
            .set(place)
            .addOnSuccessListener {
                Log.d("PlaceRepository", "DocumentSnapshot added with ID: $placeId")
            }
            .addOnFailureListener { e ->
                Log.w("PlaceRepository", "Error adding document", e)
            }
    }
    fun getPlaces(callback: (MutableList<Place>) -> Unit) {
        val places = mutableListOf<Place>()
        db.collection("places")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val place = document.toObject(Place::class.java)
                    places.add(place)
                }
                // Veriler yüklendikten sonra callback ile geri döndür
                callback(places)
            }
            .addOnFailureListener { exception ->
                Log.w("PlaceRepository", "Error getting documents.", exception)
                // Hata durumunda boş listeyi callback ile döndür
                callback(places)
            }
    }

    fun updatePlace(place: Place) {
        db.collection("places")
            .document(place.place_id)
            .set(place)
            .addOnSuccessListener {
                Log.d("PlaceRepository", "DocumentSnapshot updated with ID: ${place.place_id}")
            }
            .addOnFailureListener { e ->
                Log.w("PlaceRepository", "Error updating document", e)
            }
    }
    fun deletePlace(place: Place) {
        db.collection("places")
            .document(place.place_id)
            .delete()
            .addOnSuccessListener {
                Log.d("PlaceRepository", "DocumentSnapshot deleted with ID: ${place.place_id}")
            }
            .addOnFailureListener { e ->
                Log.w("PlaceRepository", "Error deleting document", e)
            }
    }
}