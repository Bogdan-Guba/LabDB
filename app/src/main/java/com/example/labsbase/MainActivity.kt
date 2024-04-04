import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
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
    var databaseText by remember { mutableStateOf("") }

    val cars = remember { generateDummyCars() }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Текстовое поле для ввода поискового запроса
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Поиск...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка "Искать"
        Button(
            onClick = {
                val result = filterCars(cars, searchQuery.text)
                databaseText = result.joinToString("\n\n")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Искать")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Большое текстовое поле для вывода информации из базы
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            databaseText.split("\n\n").forEach { carInfo ->
                Surface(
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = carInfo,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}


// Функция фильтрации автомобилей
fun filterCars(cars: List<Car>, query: String): List<String> {
    return cars.filter { car ->
        car.vin.contains(query, ignoreCase = true) ||
                car.mark.contains(query, ignoreCase = true) ||
                car.model.contains(query, ignoreCase = true) ||
                car.price.toString().contains(query, ignoreCase = true) ||
                car.color.contains(query, ignoreCase = true) ||
                car.engineVolume.toString().contains(query, ignoreCase = true) ||
                car.complection.contains(query, ignoreCase = true)
    }.map { car ->
        "VIN: ${car.vin}, MARK: ${car.mark}, MODEL: ${car.model}, PRICE: ${car.price}, COLOR: ${car.color}, ENGINE VOLUME: ${car.engineVolume}, COMPLECTION: ${car.complection}"
    }
}
