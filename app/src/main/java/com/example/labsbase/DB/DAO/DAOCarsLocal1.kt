package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal1

@Dao
interface DAOCarsLocal1 {
    @Insert
    fun insertUser(сarsLocal1: CarsLocal1)

    @Query("SELECT * FROM CarsLocal1")
    fun getAllUsers(): List<CarsLocal1>


}