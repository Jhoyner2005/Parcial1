package com.example.parcial1.Logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial1.Database.AppDatabase
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Repository.CasoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CasoLogic(application: Application) : AndroidViewModel(application) {

    private val repository: CasoRepository

    val todosLosCasos: StateFlow<List<CasoEntity>>

    init {
        val casoDao = AppDatabase.getDatabase(application).casoDao()
        repository = CasoRepository(casoDao)

        todosLosCasos = repository.todosLosCasos
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun insertarCaso(
        titulo: String,
        descripcion: String,
        fecha: String,
        estado: String,
        conclusion: String
    ) {
        viewModelScope.launch {
            val caso = CasoEntity(
                titulo = titulo,
                descripcion = descripcion,
                fecha = fecha,
                estado = estado,
                conclusion = conclusion
            )

            repository.insertarCaso(caso)
        }
    }

    fun actualizarCaso(caso: CasoEntity) {
        viewModelScope.launch {
            repository.actualizarCaso(caso)
        }
    }

    fun eliminarCaso(caso: CasoEntity) {
        viewModelScope.launch {
            repository.eliminarCaso(caso)
        }
    }

    suspend fun obtenerCasoPorId(id: Int): CasoEntity? {
        return repository.obtenerCasoPorId(id)
    }

    fun buscarCasos(texto: String): StateFlow<List<CasoEntity>> {
        return repository.buscarCasos(texto)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }
}