package com.basarcelebi.localguide.data

data class User(
    val id: Int,
    val name: String,
    val city: String,
    val country: String,
    val email: String,
    val password: String,
    val profileImageId: Int
)
