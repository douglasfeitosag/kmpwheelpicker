package dev.douglasfeitosa.kmpwheelpicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WheelPickerTestScreen() {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = remember { listOf("Apple", "Banana", "Orange", "Uva", "Manga") }
    var showPicker by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("No option selected") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Selected Option: ${if (selectedIndex < options.size) options[selectedIndex] else "N/A"}")
        Spacer(Modifier.height(20.dp))
        Button(onClick = { showPicker = true }) {
            Text("Show WheelPicker")
        }
        Spacer(Modifier.height(20.dp))
        Text(resultText)

        if (showPicker) {
            WheelPicker(
                label = "Choose a fruit",
                options = options,
                selectedIndex = selectedIndex,
                onOptionSelected = { index ->
                    selectedIndex = index
                    resultText = "Você selecionou: ${options[index]}"
                    showPicker = false
                },
                onDismiss = {
                    resultText = "Picker dismiss."
                    showPicker = false
                },
                confirmText = "Confirm",
                cancelText = "Cancel",
                dialogBackground = Color.LightGray,
                titleTextStyle = MaterialTheme.typography.titleLarge,
                optionTextStyle = MaterialTheme.typography.bodyMedium,
                contentPadding = PaddingValues(16.dp),
                itemSpacing = 8.dp
            )
        }
    }
}
