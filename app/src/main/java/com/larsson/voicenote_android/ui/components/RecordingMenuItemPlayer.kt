package com.larsson.voicenote_android.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenuItemPlayer(
    title: String,
    date: String,
    id: String,
    duration: String,
    progress: Float,
    color: Color = MaterialTheme.colorScheme.background,
    isFirstItem: Boolean,
// TODO take in state?
) {
    val roundedCornerShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)

    Card(
        modifier = Modifier.wrapContentHeight(),
        backgroundColor = MaterialTheme.colorScheme.background,
        elevation = 0.dp,
        shape = if (isFirstItem) roundedCornerShape else RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Row(

                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp,
                    fontFamily = SpaceGroteskFontFamily,
                    fontWeight = FontWeight.W700,
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

            LinearProgressBar(
                backgroundColor = color,
                progress = progress,
                color = MaterialTheme.colorScheme.onBackground,
                onProgressChanged = {
                },
            )
            AudioPlayerRow(
                date = date,
                onClickDelete = { TODO() },
            )
        }
    }
}

@Composable
fun AudioPlayerRow(
    date: String,
    // onClickPlay: () -> Unit,
    onClickDelete: () -> Unit,
    // isPlaying: Boolean,
) {
    var isPlaying by remember { mutableStateOf<Boolean>(false) }
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = "play",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .clickable(indication = null, interactionSource = interactionSource) {
                        isPlaying = !isPlaying
                    },
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
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

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onClickDelete.invoke() }
                        .height(30.dp)
                        .width(30.dp),

                )
            }
        }
    }
}

private const val componentName = "Recording Menu Item Player"

@SuppressLint("UnrememberedMutableState")
@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuItemPlayerPreview() {
    VoiceNote_androidTheme {
        Column {
            RecordingMenuItemPlayer(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                progress = 0.6F,
                isFirstItem = true,
                // isPlaying = false,
                // onClickPlay = { true },
            )
            Divider()
            RecordingMenuItemPlayer(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                duration = "02:21",
                progress = 0.4F,
                isFirstItem = false,
                // isPlaying = true,
                // onClickPlay = { true },
            )
            Divider()
        }
    }
}
