package com.example.parcial1.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "casos")
data class CasoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val estado: String,
    val conclusion: String
)
