package com.example.viewvault.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModel (
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "summary") val summary: String = "",
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "seen_date") val seenDate: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
){
    val titleForList: String
        get() = title
}