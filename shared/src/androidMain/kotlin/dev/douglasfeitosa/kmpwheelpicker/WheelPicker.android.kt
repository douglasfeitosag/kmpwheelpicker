package dev.douglasfeitosa.kmpwheelpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
actual fun WheelPicker(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    onDismiss: () -> Unit,
    confirmText: String,
    cancelText: String,
    dialogBackground: Color,
    titleTextStyle: TextStyle,
    optionTextStyle: TextStyle,
    contentPadding: PaddingValues,
    itemSpacing: Dp,
) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = dialogBackground,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.wrapContentHeight()
        ) {
            Column(modifier = Modifier.padding(contentPadding)) {
                Text(text = label, style = titleTextStyle)

                Spacer(Modifier.height(itemSpacing))

                Column(Modifier.fillMaxWidth()) {
                    options.forEachIndexed { index, item ->
                        Text(
                            text = item,
                            style = optionTextStyle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = itemSpacing / 2)
                                .clickable { currentIndex = index }
                        )
                    }
                }

                Spacer(Modifier.height(itemSpacing))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(cancelText, style = optionTextStyle)
                    }
                    Spacer(Modifier.width(itemSpacing))
                    TextButton(onClick = {
                        onOptionSelected(currentIndex)
                    }) {
                        Text(confirmText, style = optionTextStyle)
                    }
                }
            }
        }
    }
}
