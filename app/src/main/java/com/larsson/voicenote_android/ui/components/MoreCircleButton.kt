package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun MoreCircleButton(
    icon: ImageVector,
    onClick: () -> Unit,
    iconOffset: Dp = 0.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colorScheme.background, shape = CircleShape),
    ) {
        IconButton(onClick = onClick, modifier = Modifier.offset(y = iconOffset).padding(8.dp)) {
            Icon(modifier = Modifier.size(35.dp), imageVector = icon, contentDescription = "button", tint = MaterialTheme.colorScheme.onBackground)
        }
    }
}

private const val componentName = "More Circle Button"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun MoreCircleButtonPreview() {
    VoiceNote_androidTheme {
        Column {
            MoreCircleButton(icon = Icons.Default.Delete, onClick = {})
            Divider()
            MoreCircleButton(icon = Icons.Default.IosShare, onClick = {}, iconOffset = -1.dp)
        }
    }
}
