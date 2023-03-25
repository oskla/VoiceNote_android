package com.larsson.voicenote_android.ui.components // ktlint-disable package-name

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

enum class Variant {
    NEW_NOTE_RECORD,
    RECORDINGS_RECORD
}

@Composable
fun BottomBox(
    modifier: Modifier = Modifier,
    variant: Variant,
    onClickLeft: (() -> Unit),
    onClickRight: (() -> Unit)
) {
    var selectedButtonLeft by remember { mutableStateOf(false) }
    var selectedButtonRight by remember { mutableStateOf(false) }

    var buttonTextLeft: String
    var buttonTextRight: String
    var iconRight: ImageVector
    var iconLeft: ImageVector

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            when (variant) {
                Variant.RECORDINGS_RECORD -> {
                    iconLeft = Icons.Filled.List
                    iconRight = Icons.Filled.RadioButtonChecked
                    buttonTextLeft = "Recordings"
                    buttonTextRight = "Record"
                }
                Variant.NEW_NOTE_RECORD -> {
                    iconLeft = Icons.Filled.Add
                    iconRight = Icons.Filled.RadioButtonChecked
                    buttonTextLeft = "New note"
                    buttonTextRight = "Record"
                }
            }
            BottomBoxButton(
                text = buttonTextLeft,
                icon = iconLeft,
                modifier = Modifier.weight(1f),
                selected = selectedButtonLeft,
                onClick = {
                    selectedButtonLeft = true
                    selectedButtonRight = false
                    onClickLeft.invoke()
                }
            )
            BottomBoxButton(
                text = buttonTextRight,
                icon = iconRight,
                selected = selectedButtonRight,
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedButtonLeft = false
                    onClickRight.invoke()
                }
            )
        }
    }
}

private const val componentName = "Bottom Box"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        Column() {
            BottomBox(
                onClickLeft = {},
                onClickRight = {},
                variant = Variant.NEW_NOTE_RECORD
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            BottomBox(
                onClickLeft = {},
                onClickRight = {},
                variant = Variant.RECORDINGS_RECORD
            )
        }
    }
}
