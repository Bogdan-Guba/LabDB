package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal


@Dao
interface DAOCarsLocal1 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(сarsLocal: CarsLocal)
    @Query("SELECT * FROM CarsLocal")
    fun getAll(): List<CarsLocal>

    @Query("DELETE FROM CarsLocal WHERE VIN = :VIN")
    suspend fun deleteByVIN(VIN: String)

    @Query("DELETE FROM CarsLocal")
    fun deleteAll()
}