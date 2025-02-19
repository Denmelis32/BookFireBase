package com.example.myboofirabase.datas

import kotlinx.serialization.Serializable

@Serializable
data class AddScreenObject(
    val key: String = "",
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)