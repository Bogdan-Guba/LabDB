package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal

@Dao
interface DAOCarsLocal2 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carslocal: CarsLocal)

    @Query("SELECT * FROM CarsLocal")
    fun getAll(): List<CarsLocal>
    @Query("DELETE FROM CarsLocal WHERE VIN = :VIN")
    suspend fun deleteByVIN(VIN: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTable(carslocal2: CarsLocal){
        insert(CarsLocal("ZAB23456789012345", "Renault", "Master", 35325, "blue", 2.3f, "Base"))
        insert(CarsLocal("BCD45678901234567", "Peugeot", "3008", 32870, "black", 1.5f, "Allure Pack"))
        insert(CarsLocal("DEF67890123456789", "Volkswagen", "T-Roc", 31154, "green", 1.4f, "Style"))
        insert(CarsLocal("GHI90123456789012", "Mercedes-Benz", "V-Class", 125746, "silver", 2.1f, "AMG Style"))
        insert(CarsLocal("IJK12345678901234", "Kia", "Stonic", 24160, "white", 1.4f, "Prestige"))
        insert(CarsLocal("NOP67890123456789", "Volkswagen", "Tiguan", 40781, "gray", 2.0f, "Life"))
        insert(CarsLocal("ZAB89012345678901", "Citroen", "C3", 17041, "platinum", 1.2f, "Feel"))
        insert(CarsLocal("BCD01234567890123", "Volkswagen", "Arteon", 52307, "black", 2.0f, "R-Line"))
        insert(CarsLocal("QRS23456789012345", "Skoda", "Octavia", 33579, "red", 1.6f, "Style"));
        insert(CarsLocal("TUV45678901234567", "Toyota", "Camry", 38420, "beige", 2.5f, "LE"));
    }
    @Query("DELETE FROM CarsLocal")
    fun deleteAll()


}