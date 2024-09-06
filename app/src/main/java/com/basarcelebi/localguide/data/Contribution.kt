package com.basarcelebi.localguide.data

data class Contribution(
    val content: String,
    val rating: Float? = null, // Eğer bir puanlama varsa
    val imageUrl: String? = null // Eğer bir fotoğraf varsa
)