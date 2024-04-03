package com.example.labsbase.DB.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.labsbase.DB.DAO.DAOCarsGlobal
import com.example.labsbase.DB.DAO.DAOCarsLocal2
import com.example.labsbase.DB.Entity.CarsLocal2


@Database(entities = [CarsLocal2::class], version = 1)
abstract class LocalDB2 : RoomDatabase() {
    abstract fun CarsLocal2Dao(): DAOCarsLocal2
}