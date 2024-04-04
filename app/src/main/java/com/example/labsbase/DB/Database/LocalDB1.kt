package com.example.labsbase.DB.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.labsbase.DB.DAO.DAOCarsGlobal
import com.example.labsbase.DB.DAO.DAOCarsLocal1
import com.example.labsbase.DB.Entity.CarsGlobal
import com.example.labsbase.DB.Entity.CarsLocal

@Database(entities = [CarsLocal::class], version = 1)
abstract class LocalDB1 : RoomDatabase() {
    abstract fun CarsLocal1Dao(): DAOCarsLocal1
}