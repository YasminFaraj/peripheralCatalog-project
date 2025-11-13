package com.example.peripheralproject.data.local

import androidx.room.*
import com.example.peripheralproject.data.model.FavoriteEntity
import com.example.peripheralproject.data.model.PeripheralEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("""
        SELECT p.* FROM peripherals p
        INNER JOIN favorites f ON p.id = f.peripheralId
        ORDER BY p.name
    """)
    fun getFavoritePeripherals(): Flow<List<PeripheralEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE peripheralId = :peripheralId")
    suspend fun removeFavoriteById(peripheralId: Int)

    @Query("SELECT COUNT(*) > 0 FROM favorites WHERE peripheralId = :peripheralId")
    fun isFavorite(peripheralId: Int): Flow<Boolean>
}
