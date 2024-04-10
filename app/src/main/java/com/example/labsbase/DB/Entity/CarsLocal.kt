package com.example.labsbase.DB.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CarsLocal(
    @PrimaryKey val VIN: String,
    val Mark: String,
    val Model: String,
    val Price: Int,
    val Color:String,
    val EnginrVolume: Float,
    val Complection:String
): Serializable