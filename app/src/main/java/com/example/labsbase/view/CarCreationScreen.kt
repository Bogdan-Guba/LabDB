package com.example.labsbase.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labsbase.DB.Controller
import com.example.labsbase.DB.Entity.CarsLocal
import com.example.labsbase.MainActivity

class CarCreationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCreationContent(this)
        }
    }
}

@Composable
fun CarCreationContent(context: Context) {
    var vin by remember { mutableStateOf("") }
    var mark by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var engineVolume by remember { mutableStateOf("") }
    var completion by remember { mutableStateOf("") }
    var place:String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Створити новий автомобіль",
            color = Color.Black,
            fontSize = 24.sp,
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vin,
            onValueChange = { vin = it },
            label = { Text("VIN") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = mark,
            onValueChange = { mark = it },
            label = { Text("Mark") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = color,
            onValueChange = { color = it },
            label = { Text("Color") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = engineVolume,
            onValueChange = { engineVolume = it },
            label = { Text("Engine Volume") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = completion,
            onValueChange = { completion = it },
            label = { Text("Completion") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Place") },
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(color = Color.Black)
        )

        Button(
            onClick = {
                val newCar = CarsLocal(
                    VIN = vin,
                    Mark = mark,
                    Model = model,
                    Price = price.toIntOrNull() ?: 0,
                    Color = color,
                    EnginrVolume = engineVolume.toFloatOrNull() ?: 0.0f,
                    Complection = completion
                )
                if (Controller.VINIsFree(vin)){
                    if (place=="1"){
                        Controller.incertLocalDB1(newCar)
                    }
                    if (place=="2"){
                        Controller.incertLocalDB2(newCar)
                    }


                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }

            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Створити", color = Color.White)
        }
    }
}
