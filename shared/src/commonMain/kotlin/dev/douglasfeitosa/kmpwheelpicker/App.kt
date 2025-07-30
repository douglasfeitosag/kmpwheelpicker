package dev.douglasfeitosa.kmpwheelpicker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 0.dp),
    ) {
        WheelPickerTestScreen()
    }
}
