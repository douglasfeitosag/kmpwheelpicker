package dev.douglasfeitosa.kmpwheelpicker.entry

import androidx.compose.ui.window.ComposeUIViewController
import dev.douglasfeitosa.kmpwheelpicker.App
import platform.UIKit.UIViewController

@Suppress("FunctionName")
fun MainViewController(): UIViewController =
    ComposeUIViewController { App() }

