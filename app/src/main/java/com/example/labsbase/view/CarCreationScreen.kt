package com.example.labsbase.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.labsbase.Car
import com.example.labsbase.MainActivity

class CarCreationActivity : AppCompatActivity() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Створити новий автомобіль", color = Color.Black, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vin,
            onValueChange = { vin = it },
            label = { Text("VIN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

        Button(
            onClick = {
                val newCar = Car(
                    vin = vin,
                    mark = mark,
                    model = model,
                    price = price.toIntOrNull() ?: 0,
                    color = color,
                    engineVolume = engineVolume.toFloatOrNull() ?: 0.0f,
                    completion = completion
                )
                // добавить новую машину к списку машин
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Створити", color = Color.White)
        }
    }
}
