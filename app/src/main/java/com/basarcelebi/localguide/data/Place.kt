package com.basarcelebi.localguide.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

data class Place(
    val place_id: String = "",
    val name: String = "",
    val description: String = "",
    var isFavorited: Boolean = false,
    val category: String = "",
    val address: String = "",
    val phone: String = "",
    val website: String = "",
    val imageUrl: String = "",
    val rating: Float = 0.0f,
    val addedBy: String = ""
)











