package com.example.peripheralproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "peripherals")
data class PeripheralEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteId: String? = null,
    val name: String,
    val brand: String,
    val category: String,
    val price: Double,
    val description: String? = null
)
