package com.example.peripheralproject.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("peripherals")
    suspend fun getPeripherals(): List<PeripheralDto>
}
