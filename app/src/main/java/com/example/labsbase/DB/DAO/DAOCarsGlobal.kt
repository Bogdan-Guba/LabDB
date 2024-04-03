package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsGlobal

@Dao
interface DAOCarsGlobal {
    @Insert
    fun insertUser(сarsGlobal: CarsGlobal)

    @Query("SELECT * FROM CarsGlobal")
    fun getAllUsers(): List<CarsGlobal>


}