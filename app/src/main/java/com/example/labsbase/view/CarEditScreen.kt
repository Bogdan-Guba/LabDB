package com.example.labsbase.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labsbase.Car
import com.example.labsbase.MainActivity

@Suppress("DEPRECATION")
class CarEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val car = intent.getSerializableExtra("car") as? Car
            if (car != null) {
                CarEditScreen(car)
            } else {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun CarEditScreen(car: Car) {
    val context = LocalContext.current
    var vin by remember { mutableStateOf(car.vin) }
    var mark by remember { mutableStateOf(car.mark) }
    var model by remember { mutableStateOf(car.model) }
    var price by remember { mutableStateOf(car.price.toString()) }
    var color by remember { mutableStateOf(car.color) }
    var engineVolume by remember { mutableStateOf(car.engineVolume.toString()) }
    var completion by remember { mutableStateOf(car.completion) }

    Surface {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Редагування автомобіля",color = Color.Black, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            CarEditField("VIN", vin) { vin = it }
            CarEditField("Mark", mark) { mark = it }
            CarEditField("Model", model) { model = it }
            CarEditField("Price", price) { price = it }
            CarEditField("Color", color) { color = it }
            CarEditField("Engine Volume", engineVolume) { engineVolume = it }
            CarEditField("Completion", completion) { completion = it }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                /* сохранения изменений */
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Зберегти")
            }
        }
    }
}

@Composable
fun CarEditField(label: String, value: String, onValueChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(label, modifier = Modifier.width(100.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
        )

        Button(
            onClick = { onValueChange("") },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text("Очистити")
        }
    }
}

@Composable
fun ErrorScreen() {
    Text("Помилка: об'єкт Car не передано в інтенті")
}
