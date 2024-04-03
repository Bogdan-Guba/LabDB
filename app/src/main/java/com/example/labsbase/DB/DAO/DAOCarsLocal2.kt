package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal2

@Dao
interface DAOCarsLocal2 {
    @Insert
    fun insertUser(carslocal2: CarsLocal2)

    @Query("SELECT * FROM CarsLocal2")
    fun getAllUsers(): List<CarsLocal2>


}