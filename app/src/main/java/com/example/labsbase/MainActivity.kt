package com.example.labsbase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.labsbase.view.CarCreationActivity

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
    val completion: String,
)

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
        Car("BCD01234567890123", "Lexus", "RX", 55000, "beige", 3.5f, "Luxury"),
        Car("ZAB23456789012345", "Renault", "Master", 35325, "blue", 2.3f, "Base"),
        Car("BCD45678901234567", "Peugeot", "3008", 32870, "black", 1.5f, "Allure Pack"),
        Car("DEF67890123456789", "Volkswagen", "T-Roc", 31154, "green", 1.4f, "Style"),
        Car("GHI90123456789012", "Mercedes-Benz", "V-Class", 125746, "silver", 2.1f, "AMG Style"),
        Car("IJK12345678901234", "Kia", "Stonic", 24160, "white", 1.4f, "Prestige"),
        Car("NOP67890123456789", "Volkswagen", "Tiguan", 40781, "gray", 2.0f, "Life"),
        Car("ZAB89012345678901", "Citroen", "C3", 17041, "platinum", 1.2f, "Feel"),
        Car("BCD01234567890123", "Volkswagen", "Arteon", 52307, "black", 2.0f, "R-Line"),
        Car("QRS23456789012345", "Skoda", "Octavia", 33579, "red", 1.6f, "Style"),
        Car("TUV45678901234567", "Toyota", "Camry", 38420, "beige", 2.5f, "LE")
    )
}

@Composable
fun MainScreen() {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }
    var selectedMark by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("") }
    var selectedEngineVolume by remember { mutableStateOf("") }
    var selectedCompletion by remember { mutableStateOf("") }
    var shouldResetFilters by remember { mutableStateOf(false) }

    val cars = remember { generateDummyCars() }
    var filteredCars by remember { mutableStateOf(cars) }

    val marks = cars.map { it.mark }.distinct()
    val models = cars.map { it.model }.distinct()
    val colors = cars.map { it.color }.distinct()
    val engineVolumes = cars.map { it.engineVolume.toString() }.distinct()
    val completions = cars.map { it.completion }.distinct()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color.LightGray)
        ) {
            // Текстовое поле для ввода поискового запроса
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { newQuery ->
                    searchQuery = newQuery
                    filteredCars = filterCars(cars, newQuery.text, selectedMark, selectedModel, selectedColor, selectedEngineVolume, selectedCompletion)
                    errorMessage = if (filteredCars.isNotEmpty()) {
                        ""
                    } else {
                        "Нічого не знайдено"
                    }
                },
                onSearch = {
                    filteredCars = filterCars(cars, searchQuery.text, selectedMark, selectedModel, selectedColor, selectedEngineVolume, selectedCompletion)
                    errorMessage = if (filteredCars.isNotEmpty()) {
                        ""
                    } else {
                        "Нічого не знайдено"
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            FilterDropdown(
                options = marks,
                selectedOption = selectedMark,
                onOptionSelected = { mark ->
                    selectedMark = mark
                },
                label = "MARK"
            )
            Spacer(modifier = Modifier.width(8.dp))

            FilterDropdown(
                options = models,
                selectedOption = selectedModel,
                onOptionSelected = { model ->
                    selectedModel = model
                },
                label = "MODEL"
            )
            Spacer(modifier = Modifier.width(8.dp))

            FilterDropdown(
                options = colors,
                selectedOption = selectedColor,
                onOptionSelected = { color ->
                    selectedColor = color
                },
                label = "COLOR"
            )
            Spacer(modifier = Modifier.width(8.dp))

            FilterDropdown(
                options = engineVolumes,
                selectedOption = selectedEngineVolume,
                onOptionSelected = { engineVolume ->
                    selectedEngineVolume = engineVolume
                },
                label = "ENGINE VOLUME"
            )
            Spacer(modifier = Modifier.width(8.dp))

            FilterDropdown(
                options = completions,
                selectedOption = selectedCompletion,
                onOptionSelected = { completion ->
                    selectedCompletion = completion
                },
                label = "COMPLETION"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                // Кнопка "Применить"
                Button(
                    onClick = {
                        filteredCars = applyFilters(cars, selectedMark, selectedModel, selectedColor, selectedEngineVolume, selectedCompletion)
                        errorMessage = if(filteredCars.isEmpty()) {
                            "Нічого не знайдено"
                        } else {
                            ""
                        }
                    }
                ) {
                    Text("Застосувати")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Кнопка "Сбросить"
                Button(
                    onClick = {
                        selectedMark = ""
                        selectedModel = ""
                        selectedColor = ""
                        selectedEngineVolume = ""
                        selectedCompletion = ""
                        shouldResetFilters = !shouldResetFilters
                        filteredCars = applyFilters(cars, selectedMark, selectedModel, selectedColor, selectedEngineVolume, selectedCompletion)
                    }
                ) {
                    Text("Сбросити фільтри")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Таблица для вывода информации о машинах
            CarList(cars = filteredCars, searchQuery = searchQuery, errorMessage = errorMessage)
        }
    }
}

@Composable
fun SearchBar(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { newQuery -> onSearchQueryChange(newQuery) },
            label = { Text("Пошук...") },
            modifier = Modifier.weight(1f),
            trailingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    IconButton(onClick = { onSearch() }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        val intent = Intent(context, CarCreationActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Add",
                            tint = Color.Black
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun FilterDropdown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    label: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = selectedOption.ifEmpty { label },
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Black
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier.background(if (isSelected) Color.LightGray else Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun CarList(cars: List<Car>, searchQuery: TextFieldValue, errorMessage: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        val filteredCars = filterCars(cars, searchQuery.text, "", "", "", "", "")
        if (filteredCars.isEmpty()) {
            Text(errorMessage, color = Color.Red)
        } else {
            filteredCars.forEach { car ->
                CarItem(car = car, onDelete = { /* Логика удаления */ })
            }
        }
    }
}

@Composable
fun CarItem(car: Car, onDelete: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "VIN: ${car.vin} " +
                            "\n\nMARK: ${car.mark}" +
                            "\n\nMODEL: ${car.model}" +
                            "\n\nPRICE: ${car.price}" +
                            "\n\nCOLOR: ${car.color}" +
                            "\n\nENGINE VOLUME: ${car.engineVolume}" +
                            "\n\nCOMPLETION: ${car.completion}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                EditButton()
            }
        }
    }
}

@Composable
fun EditButton() {
    IconButton(
        onClick = { /* Действие при нажатии на кнопку редактирования */ }
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Edit",
            tint = Color.Black
        )
    }
}

// Функция фильтрации автомобилей
fun filterCars(cars: List<Car>, query: String, mark: String, model: String, color: String, engineVolume: String, completion: String): List<Car> {
    return cars.filter { car ->
        (car.vin.contains(query, ignoreCase = true) ||
                car.mark.contains(query, ignoreCase = true) ||
                car.model.contains(query, ignoreCase = true) ||
                car.price.toString().contains(query, ignoreCase = true) ||
                car.color.contains(query, ignoreCase = true) ||
                car.engineVolume.toString().contains(query, ignoreCase = true) ||
                car.completion.contains(query, ignoreCase = true)) &&
                (mark.isEmpty() || car.mark == mark) &&
                (model.isEmpty() || car.model == model) &&
                (color.isEmpty() || car.color == color) &&
                (engineVolume.isEmpty() || car.engineVolume.toString() == engineVolume) &&
                (completion.isEmpty() || car.completion == completion)
    }
}


// Функция применения фильтров
fun applyFilters(cars: List<Car>, mark: String, model: String, color: String, engineVolume: String, completion: String): List<Car> {
    return cars.filter { car ->
        (mark.isEmpty() || car.mark == mark) &&
                (model.isEmpty() || car.model == model) &&
                (color.isEmpty() || car.color == color) &&
                (engineVolume.isEmpty() || car.engineVolume.toString() == engineVolume) &&
                (completion.isEmpty() || car.completion == completion)
    }
}