package com.dicoding.asclepius.data.source.local.room

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory

@Database(entities = [AnalyzeHistory::class], version = 1, exportSchema = false)
abstract class AnalyzeRoomDatabase: RoomDatabase() {
    abstract fun analyzeDao(): AnalyzeDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyzeRoomDatabase?  = null
        fun getDatabase(context: Context) : AnalyzeRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AnalyzeRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AnalyzeRoomDatabase::class.java,"analyze_database")
                        .build()
                }
            }
            return INSTANCE as AnalyzeRoomDatabase
        }
    }
}