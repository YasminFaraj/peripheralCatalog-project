package com.example.peripheralproject.data.repository

import com.example.peripheralproject.data.model.FavoriteEntity
import com.example.peripheralproject.data.remote.ApiService
import com.example.peripheralproject.data.local.FavoriteDao
import com.example.peripheralproject.data.local.PeripheralDao
import com.example.peripheralproject.data.model.PeripheralEntity
import com.example.peripheralproject.data.remote.PeripheralDto
import kotlinx.coroutines.flow.Flow
import kotlin.collections.map

class PeripheralRepository(
    private val peripheralDao: PeripheralDao,
    private val favoriteDao: FavoriteDao,
    private val apiService: ApiService
) {

    fun getAllPeripherals(): Flow<List<PeripheralEntity>> =
        peripheralDao.getAllPeripherals()

    fun searchByName(query: String): Flow<List<PeripheralEntity>> =
        peripheralDao.searchByName(query)

    suspend fun getById(id: Int): PeripheralEntity? =
        peripheralDao.getById(id)

    suspend fun insert(peripheral: PeripheralEntity) =
        peripheralDao.insert(peripheral)

    suspend fun update(peripheral: PeripheralEntity) =
        peripheralDao.update(peripheral)

    suspend fun delete(peripheral: PeripheralEntity) =
        peripheralDao.delete(peripheral)

    fun getFavoritePeripherals(): Flow<List<PeripheralEntity>> =
        favoriteDao.getFavoritePeripherals()

    fun isFavorite(peripheralId: Int): Flow<Boolean> =
        favoriteDao.isFavorite(peripheralId)

    suspend fun toggleFavorite(peripheralId: Int, currentlyFavorite: Boolean) {
        if (currentlyFavorite) {
            favoriteDao.removeFavoriteById(peripheralId)
        } else {
            favoriteDao.addFavorite(FavoriteEntity(peripheralId))
        }
    }

    /** Sincroniza da API para o Room (se a API não estiver disponível, você pode chamar isso só uma vez ou remover Retrofit). */
    suspend fun syncFromApi() {
        val remoteList = apiService.getPeripherals()
        val entities = remoteList.map { it.toEntity() }
        peripheralDao.clearAll()
        peripheralDao.insertAll(entities)
    }

    private fun PeripheralDto.toEntity() = PeripheralEntity(
        remoteId = id,
        name = name,
        brand = brand,
        category = category,
        price = price,
        description = description
    )
}
