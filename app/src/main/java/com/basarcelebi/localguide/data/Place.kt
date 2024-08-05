package com.basarcelebi.localguide.data

data class Place(
    val name: String,
    val description: String,
    val category: String,
    val address: String,
    val phone: String,
    val website: String,
    val imageUrl: String,
    val rating: Float
)
