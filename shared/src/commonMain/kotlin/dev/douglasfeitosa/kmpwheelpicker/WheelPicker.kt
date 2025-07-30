package dev.douglasfeitosa.kmpwheelpicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

@Composable
expect fun WheelPicker(
    label: String,
    options: List<String>,
    selectedIndex: Int = 0,
    onOptionSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Select",
    cancelText: String = "Cancel",
    dialogBackground: Color = MaterialTheme.colorScheme.surface,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    optionTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    itemSpacing: Dp = 8.dp,
)