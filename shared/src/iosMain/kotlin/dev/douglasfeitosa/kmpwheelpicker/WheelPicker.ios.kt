package dev.douglasfeitosa.kmpwheelpicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readValue
import kotlinx.cinterop.value
import platform.CoreGraphics.CGRectZero
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSArray
import platform.Foundation.NSString
import platform.Foundation.arrayWithObjects
import platform.Foundation.create
import platform.Foundation.setValue
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleCancel
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleActionSheet
import platform.UIKit.UIApplication
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.UIColor
import platform.darwin.NSObject
import kotlinx.cinterop.BetaInteropApi

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
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
    itemSpacing: Dp
) {
    UIKitView(
        modifier = Modifier,
        factory = {
            val rootVC =
                UIApplication.sharedApplication.keyWindow?.rootViewController
                    ?: return@UIKitView UIView()

            var currentSelection = selectedIndex

            val nsOptions: List<*> = memScoped {
                val strings = options.map { NSString.create(string = it) }
                val cArray = allocArray<ObjCObjectVar<Any?>>(strings.size)
                strings.forEachIndexed { idx, s -> cArray[idx].value = s }
                NSArray.arrayWithObjects(cArray, strings.size.convert())
            }

            val picker = WheelPickerView(nsOptions, selectedIndex.toLong())
            picker.pickerCallback = object : NSObject(), WheelPickerDelegateProtocol {
                override fun didSelectWithIndex(index: Long) {
                    currentSelection = index.toInt()
                }
            }

            val containerVC = UIViewController(null, null).apply {
                val container = UIView().apply {
                    backgroundColor = UIColor.whiteColor
                    addSubview(picker)
                }
                setView(container)
                setPreferredContentSize(CGSizeMake(320.0, 216.0))
            }

            val alert = UIAlertController.alertControllerWithTitle(
                title = label,
                message = null,
                preferredStyle = UIAlertControllerStyleActionSheet
            )
            (alert as NSObject).setValue(containerVC, forKey = "contentViewController")

            alert.addAction(
                UIAlertAction.actionWithTitle(
                    title = confirmText,
                    style = UIAlertActionStyleDefault,
                    handler = {
                        val selectedRow = picker.selectedRowInComponent(0)
                        onOptionSelected(selectedRow.toInt())
                        onOptionSelected(currentSelection)
                        onDismiss()
                    }
                )
            )
            alert.addAction(
                UIAlertAction.actionWithTitle(
                    title = cancelText,
                    style = UIAlertActionStyleCancel,
                    handler = { onDismiss() }
                )
            )

            rootVC.presentViewController(alert, animated = true, completion = null)
            UIView(frame = CGRectZero.readValue())
        }
    )
}
