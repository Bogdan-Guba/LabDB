package com.example.labsbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.labsbase.DB.DAO.DAOCarsLocal1
import com.example.labsbase.DB.Database.CenrtalDB

import com.example.labsbase.DB.Database.LocalDB1
import com.example.labsbase.DB.Database.LocalDB2
import com.example.labsbase.DB.Entity.CarsGlobal
import com.example.labsbase.ui.theme.LabsBaseTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val CentralDB = Room.databaseBuilder(this, CenrtalDB::class.java, "CentralDB").build()
        val DaoCentralDB = CentralDB.carsGlobalDao()
        val LocalDB1 = Room.databaseBuilder(this, LocalDB1::class.java, "LocalDB1").build()
        val DaoCarsLocal1 = LocalDB1.CarsLocal1Dao()
        val LocalDB2 = Room.databaseBuilder(this, LocalDB2::class.java, "LocalDB2").build()
        val DaoCarsLocal2 = LocalDB2.CarsLocal2Dao()

        setContent {
            LabsBaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsBaseTheme {
        Greeting("Android")
    }
}