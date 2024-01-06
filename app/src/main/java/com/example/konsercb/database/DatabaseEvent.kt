package com.example.konsercb.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Event::class], version = 1, exportSchema = false)
abstract class DatabaseEvent : RoomDatabase() {

    abstract fun EventDAO(): EventDAO

    companion object {
        @Volatile
        private var instance: DatabaseEvent? = null

        fun getDatabase(context: Context): DatabaseEvent {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DatabaseEvent {
            return Room.databaseBuilder(
                context.applicationContext,
                DatabaseEvent::class.java,
                "event_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
