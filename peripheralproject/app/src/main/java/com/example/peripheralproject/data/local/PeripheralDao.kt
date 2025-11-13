package com.example.peripheralproject.data.local

import androidx.room.*
import com.example.peripheralproject.data.model.PeripheralEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PeripheralDao {

    @Query("SELECT * FROM peripherals ORDER BY name")
    fun getAllPeripherals(): Flow<List<PeripheralEntity>>

    @Query("SELECT * FROM peripherals WHERE id = :id")
    suspend fun getById(id: Int): PeripheralEntity?

    @Query("SELECT * FROM peripherals WHERE name LIKE '%' || :query || '%' ORDER BY name")
    fun searchByName(query: String): Flow<List<PeripheralEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(peripheral: PeripheralEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(peripherals: List<PeripheralEntity>)

    @Update
    suspend fun update(peripheral: PeripheralEntity)

    @Delete
    suspend fun delete(peripheral: PeripheralEntity)

    @Query("DELETE FROM peripherals")
    suspend fun clearAll()
}
