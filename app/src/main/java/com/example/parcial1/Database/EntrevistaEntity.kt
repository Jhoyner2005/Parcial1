package com.example.parcial1.Database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "entrevistas",
    foreignKeys = [
        ForeignKey(
            entity = CasoEntity::class,
            parentColumns = ["id"],
            childColumns = ["casoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("casoId")]
)
data class EntrevistaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val casoId: Int,
    val entrevistado: String,
    val fecha: String,
    val hallazgos: String
)