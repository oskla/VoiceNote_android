package com.larsson.voicenote_android.ui.components // ktlint-disable package-name

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.VoiceNoteTheme

@Composable
fun BottomBoxButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector,
    iconOffset: Dp = 0.dp,
    onClick: (() -> Unit),
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onClick.invoke()
            },
    ) {
        Box(
            modifier = Modifier
                .padding(end = 6.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .offset(iconOffset),
            )
        }
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
        )
    }
}

private const val componentName = "Bottom Box Button"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun Preview2Component() {
    VoiceNoteTheme {
        Column() {
            BottomBoxButton(
                text = "New note",
                icon = Icons.Filled.Add,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                onClick = {},
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            BottomBoxButton(
                text = "Record",
                icon = Icons.Filled.RadioButtonChecked,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                onClick = {},
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            BottomBoxButton(
                text = "Recordings",
                icon = Icons.Filled.List,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                onClick = {},
            )
        }
    }
}
