package com.example.peripheralproject.data.remote

data class PeripheralDto(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val imageUrl: String?,
    val description: String?
)
