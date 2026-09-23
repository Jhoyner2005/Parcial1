package com.example.parcial1.Logic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcial1.Database.CasoEntity
import com.example.parcial1.Database.EntrevistaEntity
import com.example.parcial1.Repository.CasoRepository
import com.example.parcial1.Repository.EntrevistaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CasoLogic(
    application: Application,
    private val repository: CasoRepository,
    private val entrevistaRepository: EntrevistaRepository
) : AndroidViewModel(application) {

    val todosLosCasos: StateFlow<List<CasoEntity>> = repository.todosLosCasos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // ==================== CASOS ====================

    fun insertarCaso(
        titulo: String,
        descripcion: String,
        fecha: String,
        estado: String,
        conclusion: String
    ) {
        viewModelScope.launch {
            repository.insertarCaso(
                CasoEntity(
                    titulo = titulo,
                    descripcion = descripcion,
                    fecha = fecha,
                    estado = estado,
                    conclusion = conclusion
                )
            )
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

    // ==================== ENTREVISTAS ====================

    fun obtenerEntrevistasPorCaso(casoId: Int): StateFlow<List<EntrevistaEntity>> {
        return entrevistaRepository.obtenerEntrevistasPorCaso(casoId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun insertarEntrevista(
        casoId: Int,
        entrevistado: String,
        fecha: String,
        hallazgos: String,
        evidencias: String
    ) {
        viewModelScope.launch {
            entrevistaRepository.insertarEntrevista(
                EntrevistaEntity(
                    casoId = casoId,
                    entrevistado = entrevistado,
                    fecha = fecha,
                    hallazgos = hallazgos,
                    evidencias = evidencias
                )
            )
        }
    }

    fun actualizarEntrevista(entrevista: EntrevistaEntity) {
        viewModelScope.launch {
            entrevistaRepository.actualizarEntrevista(entrevista)
        }
    }

    fun eliminarEntrevista(entrevista: EntrevistaEntity) {
        viewModelScope.launch {
            entrevistaRepository.eliminarEntrevista(entrevista)
        }
    }
}