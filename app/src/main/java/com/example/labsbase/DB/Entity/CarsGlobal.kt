package com.example.labsbase.DB.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarsGlobal(
    @PrimaryKey val VIN: String,
    @ColumnInfo val Mark: String,
    @ColumnInfo val Model: String,
    @ColumnInfo val Place: Int,
    @ColumnInfo val Price: Int
)