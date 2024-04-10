@file:Suppress("DEPRECATION")

package com.example.labsbase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.labsbase.DB.Controller
import com.example.labsbase.DB.Entity.CarsLocal
import com.example.labsbase.view.CarCreationActivity
import com.example.labsbase.view.CarEditActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Controller.initialize(this.applicationContext)
        Controller.insertTableLocalDB1()
        Controller.insertTableLocalDB2()
        setContent {
            MainScreen(Controller.getAllOfLocal().toMutableList())
        }


    }
}

@Composable
fun MainScreen(Cars:MutableList<CarsLocal>) {
    var searchQuery by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }
    var selectedMark by remember { mutableStateOf("") }
    var selectedModel by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("") }
    var selectedEngineVolume by remember { mutableStateOf("") }
    var selectedCompletion by remember { mutableStateOf("") }
    var shouldResetFilters by remember { mutableStateOf(false) }

    var cars: MutableList<CarsLocal> = remember { Cars}
    var filteredCars by remember { mutableStateOf(cars) }

    val marks = cars.map { it.Mark }.distinct()
    val models = cars.map { it.Model }.distinct()
    val colors = cars.map { it.Color }.distinct()
    val engineVolumes = cars.map { it.EnginrVolume.toString() }.distinct()
    val completions = cars.map { it.Complection }.distinct()

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
fun CarList(cars: MutableList<CarsLocal>, searchQuery: TextFieldValue, errorMessage: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        var filteredCars = filterCars(cars, searchQuery.text, "", "", "", "", "")
        if (filteredCars.isEmpty()) {
            Text(errorMessage, color = Color.Red)
        } else {
            filteredCars.forEach { car ->
                CarItem(car = car, onDelete = {
                    Controller.DeleteInAllDB(car.VIN)
                    cars.remove(car)
                })
            }
        }
    }
}

@Composable
fun CarItem(car: CarsLocal, onDelete: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) } // Track visibility state

    AnimatedVisibility(
        isVisible, exit = fadeOut() // Animate fade out on exit
    ) {
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
                        text = "VIN: ${car.VIN} " +
                                "\n\nMARK: ${car.Mark}" +
                                "\n\nMODEL: ${car.Model}" +
                                "\n\nPRICE: ${car.Price}" +
                                "\n\nCOLOR: ${car.Color}" +
                                "\n\nENGINE VOLUME: ${car.EnginrVolume}" +
                                "\n\nCOMPLETION: ${car.Complection}",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            isVisible = false // Set invisible on delete
                            onDelete()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    EditButton(car = car)
                }
            }
        }
    }
}

@Composable
fun EditButton(car: CarsLocal) {
    val context = LocalContext.current
    IconButton(
        onClick = {
            val intent = Intent(context, CarEditActivity::class.java)
            intent.putExtra("car", car)
            context.startActivity(intent)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Edit",
            tint = Color.Black
        )
    }
}

// Функция фильтрации автомобилей
fun filterCars(cars: MutableList<CarsLocal>, query: String, mark: String, model: String, color: String, engineVolume: String, completion: String): MutableList<CarsLocal> {
    return cars.filter { car ->
        (car.VIN.contains(query, ignoreCase = true) ||
                car.Mark.contains(query, ignoreCase = true) ||
                car.Model.contains(query, ignoreCase = true) ||
                car.Price.toString().contains(query, ignoreCase = true) ||
                car.Color.contains(query, ignoreCase = true) ||
                car.EnginrVolume.toString().contains(query, ignoreCase = true) ||
                car.Complection.contains(query, ignoreCase = true)) &&
                (mark.isEmpty() || car.Mark == mark) &&
                (model.isEmpty() || car.Model == model) &&
                (color.isEmpty() || car.Color == color) &&
                (engineVolume.isEmpty() || car.EnginrVolume.toString() == engineVolume) &&
                (completion.isEmpty() || car.Complection == completion)
    }.toMutableList()
}


// Функция применения фильтров
fun applyFilters(cars: MutableList<CarsLocal>, mark: String, model: String, color: String, engineVolume: String, completion: String): MutableList<CarsLocal> {
    return cars.filter { CarsLocal ->
        (mark.isEmpty() || CarsLocal.Mark == mark) &&
                (model.isEmpty() || CarsLocal.Model == model) &&
                (color.isEmpty() || CarsLocal.Color == color) &&
                (engineVolume.isEmpty() || CarsLocal.EnginrVolume.toString() == engineVolume) &&
                (completion.isEmpty() || CarsLocal.Complection == completion)
    }.toMutableList()
}