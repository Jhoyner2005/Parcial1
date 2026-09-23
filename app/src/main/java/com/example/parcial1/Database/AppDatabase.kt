package com.example.parcial1.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CasoEntity::class, EntrevistaEntity::class],
    version = 2,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun casoDao(): CasoDao
    abstract fun entrevistaDao(): EntrevistaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "casos_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}