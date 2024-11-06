package com.dicoding.asclepius.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory

@Dao
interface AnalyzeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analyzeHistory:AnalyzeHistory)

    @Delete
    suspend fun delete(analyzeHistory:AnalyzeHistory)

    @Query("SELECT * FROM AnalyzeHistory")
    fun getHistory(): LiveData<List<AnalyzeHistory>>
}