package com.example.labsbase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.labsbase.DB.Controller
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Controller.initialize(this)
        setContent {
            MainScreen()
        }







    }
    @Composable
    fun MainScreen() {
        var vin by remember { mutableStateOf(TextFieldValue()) }
        var databaseText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Текстовое поле для ввода VIN
            TextField(
                value = vin,
                onValueChange = { vin = it },
                label = { Text("VIN") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* Заполнить */ }) {
                    Text("Заполнить")
                }
                Button(onClick = { /* Удалить */ }) {
                    Text("Удалить")
                }
                Button(onClick = { /* Обновить */ }) {
                    Text("Обновить")
                }
                Button(onClick = { /* Изменить */ }) {
                    Text("Изменить")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Большое текстовое поле для вывода информации из базы
            BasicTextField(
                value = databaseText,
                onValueChange = { databaseText = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }
}


