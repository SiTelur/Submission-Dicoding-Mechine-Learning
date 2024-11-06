package com.dicoding.asclepius.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AnalyzeHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id : Int = 0,

    @ColumnInfo("imageUri")
    val imageUri : String? = null,

    @ColumnInfo("insertedDate")
    val insertedDate : String? = null,

    @ColumnInfo("resultRate")
    val resultRate : String ?  = null
): Parcelable
