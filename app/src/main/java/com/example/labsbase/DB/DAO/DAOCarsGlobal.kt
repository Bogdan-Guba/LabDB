package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsGlobal

@Dao
interface DAOCarsGlobal {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(сarsGlobal: CarsGlobal)

    @Query("DELETE FROM CarsGlobal WHERE VIN = :VIN")
    suspend fun deleteByVIN(VIN: String)
    @Query("SELECT * FROM CarsGlobal WHERE VIN = :VIN")
    suspend fun VinFree(VIN: String):List<CarsGlobal>

    @Query("SELECT Place FROM CarsGlobal WHERE VIN = :VIN")
    fun PlaceByVIN(VIN: String):Int



    @Query("SELECT * FROM CarsGlobal")
    fun getAll(): List<CarsGlobal>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replication(сarsGlobal: CarsGlobal){

    }
    @Query("DELETE FROM CarsGlobal")
    fun deleteAll()

}