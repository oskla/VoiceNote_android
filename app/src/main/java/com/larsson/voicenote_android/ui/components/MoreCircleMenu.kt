package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun MoreCircleMenu(
    modifier: Modifier = Modifier,
    onClickDelete: () -> Unit,
    onClickShare: () -> Unit,
    onClickDismiss: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clickable { onClickDismiss.invoke() }
            .background(Color.Black.copy(0.6f)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        MoreCircleButton(icon = Icons.Default.Delete, onClick = onClickDelete)
        Spacer(modifier = Modifier.width(6.dp))
        MoreCircleButton(icon = Icons.Default.IosShare, onClick = { /*TODO*/ }, iconOffset = (-1).dp)
    }
}

private const val componentName = "MoreCircleMenu"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun MoreCircleMenuPreview() {
    VoiceNote_androidTheme {
        Column {
            MoreCircleMenu(onClickDelete = {}, onClickShare = {}, onClickDismiss = {})
        }
    }
}
