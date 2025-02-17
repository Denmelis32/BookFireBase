package com.example.myboofirabase.login.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObeject (
    val uid: String = "",
    val email: String = ""
)