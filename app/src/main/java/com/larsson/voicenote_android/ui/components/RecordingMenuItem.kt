package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenuItemBase(
    title: String,
    date: String,
    id: String,
    duration: String,
) {
    Card(
        backgroundColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.wrapContentHeight(),
        elevation = 0.dp,
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = SpaceGroteskFontFamily,
                fontWeight = FontWeight.W700,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Thin,
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                    fontSize = 14.sp,
                    maxLines = 2,
                )
                Text(
                    text = duration,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.Thin,
                    style = LocalTextStyle.current.copy(lineHeight = 15.sp),
                    fontSize = 14.sp,
                    maxLines = 2,
                )
            }
        }
    }
}

@Composable
fun RecordingMenuItem(
    title: String,
    date: String,
    id: String,
    duration: String,
    isPlaying: Boolean?, // TODO maybe this will come from viewmodel
    progress: Float?,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .background(MaterialTheme.colorScheme.secondary),

    ) {
        if (isSelected) {
            RecordingMenuItemPlayer(
                title = title,
                date = date,
                id = id,
                duration = duration,
                isPlaying = isPlaying ?: false,
                progress = progress ?: 0f,
            )
        } else {
            RecordingMenuItemBase(
                title = title,
                date = date,
                id = id,
                duration = duration,
            )
        }
    }
}

private const val componentName = "Recording Menu Item"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuItemPreview() {
    VoiceNote_androidTheme {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            RecordingMenuItem(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                progress = 0.4F,
                isPlaying = true,
                isSelected = true,
                onClick = {
                },
            )
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            RecordingMenuItem(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                progress = null,
                isPlaying = null,
                isSelected = false,
                onClick = { },
            )
        }
    }
}
