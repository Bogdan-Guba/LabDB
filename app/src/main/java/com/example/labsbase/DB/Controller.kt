package com.example.labsbase.DB

import android.content.Context
import androidx.room.Room

import com.example.labsbase.DB.DAO.DAOCarsGlobal
import com.example.labsbase.DB.DAO.DAOCarsLocal1
import com.example.labsbase.DB.DAO.DAOCarsLocal2

import com.example.labsbase.DB.Database.CentralDB
import com.example.labsbase.DB.Database.LocalDB1
import com.example.labsbase.DB.Database.LocalDB2
import com.example.labsbase.DB.Entity.CarsGlobal
import com.example.labsbase.DB.Entity.CarsLocal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Controller {
    private lateinit var context: Context
    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
    val centralDB: CentralDB by lazy {
        Room.databaseBuilder(context, CentralDB::class.java, "CentralDB").build()
    }

    val daoCentralDB: DAOCarsGlobal by lazy {
        centralDB.carsGlobalDao()
    }

    val localDB1: LocalDB1 by lazy {
        Room.databaseBuilder(context, LocalDB1::class.java, "LocalDB1").build()
    }

    val daoCarsLocal1: DAOCarsLocal1 by lazy {
        localDB1.CarsLocal1Dao()
    }

    val localDB2: LocalDB2 by lazy {
        Room.databaseBuilder(context, LocalDB2::class.java, "LocalDB2").build()
    }

    val daoCarsLocal2: DAOCarsLocal2 by lazy {
        localDB2.CarsLocal2Dao()
    }

    fun incertCentralDB(carsGlobal:CarsGlobal){
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {
                try {
                     daoCentralDB.insert(carsGlobal)
                }catch (e:Exception){
                    print("Error in incert central")
                }


            }
        }

    }

    fun incertLocalDB1(carsLocal1:CarsLocal){
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {

                try{
                    daoCarsLocal1.insert(carsLocal1)
                }catch (e:Exception){
                    //Log.e("Error in daoCarsLocal1")
                    print("Error in incert local 1")
                }
                try{
                    daoCentralDB.insert(CarsGlobal(carsLocal1.VIN, carsLocal1.Mark, carsLocal1.Model,1, carsLocal1.Price))
                }catch (e:Exception){
                    print("Error in replication from 1 to global")
                }

            }
    }
}

    fun incertLocalDB2(carsLocal2: CarsLocal){
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {
                try{
                daoCarsLocal2.insert(carsLocal2)
                }catch (e:Exception){
                    //Log.e("Error in daoCarsLocal2")
                    print("Error in incert local 2")
                }
                try{
                daoCentralDB.insert(CarsGlobal(carsLocal2.VIN, carsLocal2.Mark, carsLocal2.Model,2, carsLocal2.Price))
                }catch (e:Exception){
                    print("Error in replication from 2 to global")
                }

            }
        }
    }


    fun insertTableLocalDB1() {
        incertLocalDB1(CarsLocal("ABC12345678901234", "Toyota", "Camry", 25000, "blue", 2.5f, "Basic"))
        incertLocalDB1(CarsLocal("DEF23456789012345", "Honda", "Accord", 28000, "red", 2.0f, "Premium"))
        incertLocalDB1(CarsLocal("GHI34567890123456", "Ford", "Mustang", 35000, "yellow", 3.0f, "Premium"))
        incertLocalDB1(CarsLocal("JKL45678901234567", "Chevrolet", "Cruze", 23000, "white", 1.8f, "Standard"))
        incertLocalDB1(CarsLocal("MNO56789012345678", "BMW", "X5", 60000, "black", 4.0f, "Luxury"))
        incertLocalDB1(CarsLocal("PQR67890123456789", "Mercedes-Benz", "E-Class", 55000, "silver", 3.5f, "Premium"))
        incertLocalDB1(CarsLocal("STU78901234567890", "Audi", "A4", 42000, "grey", 2.0f, "Premium"))
        incertLocalDB1(CarsLocal("VWX89012345678901", "Volkswagen", "Golf", 28000, "green", 1.6f, "Standard"))
        incertLocalDB1(CarsLocal("YZA90123456789012", "Subaru", "Forester", 32000, "orange", 2.5f, "Premium"))
        incertLocalDB1(CarsLocal("BCD01234567890123", "Lexus", "RX", 55000, "beige", 3.5f, "Luxury"))
    }


    fun insertTableLocalDB2(){
        incertLocalDB2(CarsLocal("ZAB23456789012345", "Renault", "Master", 35325, "blue", 2.3f, "Base"))
        incertLocalDB2(CarsLocal("BCD45678901234567", "Peugeot", "3008", 32870, "black", 1.5f, "Allure Pack"))
        incertLocalDB2(CarsLocal("DEF67890123456789", "Volkswagen", "T-Roc", 31154, "green", 1.4f, "Style"))
        incertLocalDB2(CarsLocal("GHI90123456789012", "Mercedes-Benz", "V-Class", 125746, "silver", 2.1f, "AMG Style"))
        incertLocalDB2(CarsLocal("IJK12345678901234", "Kia", "Stonic", 24160, "white", 1.4f, "Prestige"))
        incertLocalDB2(CarsLocal("NOP67890123456789", "Volkswagen", "Tiguan", 40781, "gray", 2.0f, "Life"))
        incertLocalDB2(CarsLocal("ZAB89012345678901", "Citroen", "C3", 17041, "platinum", 1.2f, "Feel"))
        incertLocalDB2(CarsLocal("BCD01234567890123", "Volkswagen", "Arteon", 52307, "black", 2.0f, "R-Line"))
        incertLocalDB2(CarsLocal("QRS23456789012345", "Skoda", "Octavia", 33579, "red", 1.6f, "Style"));
        incertLocalDB2(CarsLocal("TUV45678901234567", "Toyota", "Camry", 38420, "beige", 2.5f, "LE"));
    }

    suspend fun DeleteInAllDB(VIN:String) {
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    daoCentralDB.deleteByVIN(VIN.toString())
                } catch (e: Exception) {
                    print("problems in deletingCentral")
                }
                try {
                    daoCarsLocal1.deleteByVIN(VIN.toString())
                } catch (e: Exception) {
                    print("problems in deleting Local1")
                }
                try {
                    daoCarsLocal2.deleteByVIN(VIN.toString())
                } catch (e: Exception) {
                    print("problems in deletingLocal2")
                }
            }
        }
    }
    fun getAllOfLocal():List<CarsLocal>{
        var total= mutableListOf<CarsLocal>()
        var list1=mutableListOf<CarsLocal>()
        var list2=mutableListOf<CarsLocal>()
        CoroutineScope(Dispatchers.Main).launch {

            val result = withContext(Dispatchers.IO) {
                try{
                   list1=daoCarsLocal1.getAll().toMutableList()
                }catch (e:Exception){
                    print("Error in get first")
                }
                try{
                    list2=daoCarsLocal2.getAll().toMutableList()
                }catch (e:Exception){
                    print("Error in get 2")
                }
            }

        }
        total.addAll(list1)
        total.addAll(list2)
        return total
    }

}
