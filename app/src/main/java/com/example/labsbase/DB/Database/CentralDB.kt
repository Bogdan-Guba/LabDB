package com.example.labsbase.DB.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.labsbase.DB.DAO.DAOCarsGlobal
import com.example.labsbase.DB.Entity.CarsGlobal

@Database(entities = [CarsGlobal::class], version = 1)
abstract class CenrtalDB : RoomDatabase() {
    abstract fun carsGlobalDao(): DAOCarsGlobal
}
