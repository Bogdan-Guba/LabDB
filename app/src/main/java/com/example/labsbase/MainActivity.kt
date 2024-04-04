package com.example.labsbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

data class Car(
    val vin: String,
    val mark: String,
    val model: String,
    val price: Int,
    val color: String,
    val engineVolume: Float,
    val complection: String
)

@Composable
fun MainScreen() {
    var vin by remember { mutableStateOf(TextFieldValue()) }
    var mark by remember { mutableStateOf(TextFieldValue()) }
    var model by remember { mutableStateOf(TextFieldValue()) }
    var price by remember { mutableStateOf(TextFieldValue()) }
    var color by remember { mutableStateOf(TextFieldValue()) }
    var engineVolume by remember { mutableStateOf(TextFieldValue()) }
    var complection by remember { mutableStateOf(TextFieldValue()) }
    var databaseText by remember { mutableStateOf("") }

    val cars = remember { generateDummyCars() }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Текстовые поля для ввода параметров поиска
        TextField(
            value = vin,
            onValueChange = { vin = it },
            label = { Text("VIN") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = mark,
            onValueChange = { mark = it },
            label = { Text("MARK") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("MODEL") },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Искать"
        Button(onClick = {
            // Вызов функции поиска с передачей параметров и обновление текста базы данных
            val result = filterCars(cars, vin.text, mark.text, model.text, price.text, color.text, engineVolume.text, complection.text)
            databaseText = result.joinToString("\n")
        }) {
            Text("Искать")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Большое текстовое поле для вывода информации из базы
        BasicTextField(
            value = databaseText,
            onValueChange = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Заглушка для создания списка автомобилей
fun generateDummyCars(): List<Car> {
    return listOf(
        Car("ABC12345678901234", "Toyota", "Camry", 25000, "blue", 2.5f, "Basic"),
        Car("DEF23456789012345", "Honda", "Accord", 28000, "red", 2.0f, "Premium"),
        Car("GHI34567890123456", "Ford", "Mustang", 35000, "yellow", 3.0f, "Premium"),
        Car("JKL45678901234567", "Chevrolet", "Cruze", 23000, "white", 1.8f, "Standard"),
        Car("MNO56789012345678", "BMW", "X5", 60000, "black", 4.0f, "Luxury"),
        Car("PQR67890123456789", "Mercedes-Benz", "E-Class", 55000, "silver", 3.5f, "Premium"),
        Car("STU78901234567890", "Audi", "A4", 42000, "grey", 2.0f, "Premium"),
        Car("VWX89012345678901", "Volkswagen", "Golf", 28000, "green", 1.6f, "Standard"),
        Car("YZA90123456789012", "Subaru", "Forester", 32000, "orange", 2.5f, "Premium"),
        Car("BCD01234567890123", "Lexus", "RX", 55000, "beige", 3.5f, "Luxury")
    )
}

// Функция фильтрации автомобилей
fun filterCars(
    cars: List<Car>,
    vin: String,
    mark: String,
    model: String,
    price: String,
    color: String,
    engineVolume: String,
    complection: String
): List<String> {
    return cars.filter { car ->
        car.vin.contains(vin) &&
                car.mark.contains(mark) &&
                car.model.contains(model) &&
                car.price.toString().contains(price) &&
                car.color.contains(color) &&
                car.engineVolume.toString().contains(engineVolume) &&
                car.complection.contains(complection)
    }.map { car ->
        "VIN: ${car.vin}, MARK: ${car.mark}, MODEL: ${car.model}, PRICE: ${car.price}, COLOR: ${car.color}, ENGINE VOLUME: ${car.engineVolume}, COMPLECTION: ${car.complection}"
    }
}
