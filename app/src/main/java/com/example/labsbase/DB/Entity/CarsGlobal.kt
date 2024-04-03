package com.example.labsbase.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarsGlobal(
    @PrimaryKey val VIN: String,
    val Mark: String,
    val Model: Int,
    val Place: Int,
    val Price: Int
)