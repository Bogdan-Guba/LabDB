package com.example.labsbase.DB.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labsbase.DB.Entity.CarsLocal2

@Dao
interface DAOCarsLocal2 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(carslocal2: CarsLocal2)

    @Query("SELECT * FROM CarsLocal2")
    fun getAll(): List<CarsLocal2>
    @Query("DELETE FROM CarsLocal2 WHERE VIN = :VIN")
    suspend fun deleteByVIN(VIN: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTable(carslocal2: CarsLocal2){
        insert(CarsLocal2("ZAB23456789012345", "Renault", "Master", 35325, "blue", 2.3f, "Base"))
        insert(CarsLocal2("BCD45678901234567", "Peugeot", "3008", 32870, "black", 1.5f, "Allure Pack"))
        insert(CarsLocal2("DEF67890123456789", "Volkswagen", "T-Roc", 31154, "green", 1.4f, "Style"))
        insert(CarsLocal2("GHI90123456789012", "Mercedes-Benz", "V-Class", 125746, "silver", 2.1f, "AMG Style"))
        insert(CarsLocal2("IJK12345678901234", "Kia", "Stonic", 24160, "white", 1.4f, "Prestige"))
        insert(CarsLocal2("NOP67890123456789", "Volkswagen", "Tiguan", 40781, "gray", 2.0f, "Life"))
        insert(CarsLocal2("ZAB89012345678901", "Citroen", "C3", 17041, "platinum", 1.2f, "Feel"))
        insert(CarsLocal2("BCD01234567890123", "Volkswagen", "Arteon", 52307, "black", 2.0f, "R-Line"))
        insert(CarsLocal2("QRS23456789012345", "Skoda", "Octavia", 33579, "red", 1.6f, "Style"));
        insert(CarsLocal2("TUV45678901234567", "Toyota", "Camry", 38420, "beige", 2.5f, "LE"));
    }
    @Query("DELETE FROM CarsLocal2")
    fun deleteAll()


}