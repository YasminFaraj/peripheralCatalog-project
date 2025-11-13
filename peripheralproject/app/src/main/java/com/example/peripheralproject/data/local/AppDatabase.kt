package com.example.peripheralproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.peripheralproject.data.local.FavoriteDao
import com.example.peripheralproject.data.model.FavoriteEntity
import com.example.peripheralproject.data.model.PeripheralEntity

@Database(
    entities = [PeripheralEntity::class, FavoriteEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun peripheralDao(): PeripheralDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pericatalog_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
