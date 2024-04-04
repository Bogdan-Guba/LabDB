package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal1


@Dao
interface DAOCarsLocal1 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(сarsLocal1: CarsLocal1)
    @Query("SELECT * FROM CarsLocal1")
    fun getAll(): List<CarsLocal1>

    @Query("DELETE FROM CarsLocal1 WHERE VIN = :VIN")
    suspend fun deleteByVIN(VIN: String)

    @Query("DELETE FROM CarsLocal1")
    fun deleteAll()
}