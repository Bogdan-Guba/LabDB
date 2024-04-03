package com.example.labsbase.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarsLocal2(
    @PrimaryKey val VIN: String,
    val Mark: String,
    val Model: String,
    val Price: Int,
    val Color:String,
    val EnginrVolume: Float,
    val Complection:String
)