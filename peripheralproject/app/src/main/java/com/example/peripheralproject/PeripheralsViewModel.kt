package com.example.peripheralproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.peripheralproject.data.local.AppDatabase
import com.example.peripheralproject.data.model.PeripheralEntity
import com.example.peripheralproject.data.remote.RetrofitProvider
import com.example.peripheralproject.data.repository.PeripheralRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PeripheralsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val repository = PeripheralRepository(
        db.peripheralDao(),
        db.favoriteDao(),
        RetrofitProvider.api
    )

    private val _peripherals = MutableStateFlow<List<PeripheralEntity>>(emptyList())
    val peripherals: StateFlow<List<PeripheralEntity>> = _peripherals.asStateFlow()

    private val _favorites = MutableStateFlow<List<PeripheralEntity>>(emptyList())
    val favorites: StateFlow<List<PeripheralEntity>> = _favorites.asStateFlow()

    private val _selectedPeripheral = MutableStateFlow<PeripheralEntity?>(null)
    val selectedPeripheral: StateFlow<PeripheralEntity?> = _selectedPeripheral.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    
    private val _selectedForComparison = MutableStateFlow<List<Int>>(emptyList())
    val selectedForComparison: StateFlow<List<Int>> = _selectedForComparison.asStateFlow()

    init {
        loadPeripherals()
    }

    fun loadPeripherals() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Tenta sincronizar com a API (se der erro, usa só o local)
                runCatching { repository.syncFromApi() }
                repository.getAllPeripherals().collectLatest {
                    _peripherals.value = it
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }

    fun refreshFavorites() {
        viewModelScope.launch {
            repository.getFavoritePeripherals().collectLatest {
                _favorites.value = it
            }
        }
    }

    fun selectPeripheral(id: Int) {
        viewModelScope.launch {
            _selectedPeripheral.value = repository.getById(id)
        }
    }

    fun updateSearch(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                repository.getAllPeripherals().collectLatest {
                    _peripherals.value = it
                }
            } else {
                repository.searchByName(query).collectLatest {
                    _peripherals.value = it
                }
            }
        }
    }

    fun isFavorite(peripheralId: Int): Flow<Boolean> =
        repository.isFavorite(peripheralId)

    fun toggleFavorite(peripheralId: Int, currentlyFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(peripheralId, currentlyFavorite)
        }
    }

    fun toggleCompareSelection(peripheralId: Int) {
        val current = _selectedForComparison.value
        _selectedForComparison.value = if (current.contains(peripheralId)) {
            current - peripheralId
        } else {
            if (current.size >= 3) current else current + peripheralId
        }
    }

    fun clearComparisonSelection() {
        _selectedForComparison.value = emptyList<Int>()
    }

    fun savePeripheral(
        existingId: Int?,
        name: String,
        brand: String,
        category: String,
        price: Double,
        description: String?
    ) {
        viewModelScope.launch {
            if (existingId == null) {
                repository.insert(
                    PeripheralEntity(
                        name = name,
                        brand = brand,
                        category = category,
                        price = price,
                        description = description
                    )
                )
            } else {
                val current = repository.getById(existingId)
                if (current != null) {
                    repository.update(
                        current.copy(
                            name = name,
                            brand = brand,
                            category = category,
                            price = price,
                            description = description
                        )
                    )
                }
            }
        }
    }

    fun deletePeripheral(id: Int) {
        viewModelScope.launch {
            val current = repository.getById(id)
            if (current != null) {
                repository.delete(current)
            }
        }
    }
}
