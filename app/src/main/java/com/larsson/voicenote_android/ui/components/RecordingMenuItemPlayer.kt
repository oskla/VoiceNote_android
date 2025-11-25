package com.larsson.voicenote_android.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.larsson.voicenote_android.clicklisteners.UiAudioPlayerClickListener
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.ui.theme.SpaceGroteskFontFamily
import com.larsson.voicenote_android.ui.theme.VoiceNoteTheme

@Composable
internal fun RecordingMenuItemPlayer(
    title: String,
    date: String,
    id: String,
    durationText: String,
    durationFloat: Float,
    progress: State<Long>,
    color: Color = MaterialTheme.colorScheme.background,
    isFirstItem: Boolean,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    onClickDelete: () -> Unit,
    isPlaying: State<Boolean>,
    uiAudioPlayerClickListener: UiAudioPlayerClickListener
) {
    val roundedCornerShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)

    Card(
        modifier = Modifier.wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(),
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
                    text = durationText,
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
                currentPosition = progress,
                durationFloat = durationFloat,
                color = MaterialTheme.colorScheme.onBackground,
                uiAudioPlayerClickListener = uiAudioPlayerClickListener
            )
            AudioPlayerRow(
                date = date,
                onClickDelete = onClickDelete,
                onClickPlay = onClickPlay,
                onClickPause = onClickPause,
                isPlaying = isPlaying,
            )
        }
    }
}

@Composable
fun AudioPlayerRow(
    date: String,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    onClickDelete: () -> Unit,
    isPlaying: State<Boolean>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center,
        ) {
            val icon = if (isPlaying.value) Icons.Filled.Pause else Icons.Filled.PlayArrow
            Icon(
                imageVector = icon,
                contentDescription = "play",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
                    .clickable(indication = null, interactionSource = interactionSource) {
                        Log.d("RecordingMenuItem", "isPlaying: $isPlaying")
                        if (isPlaying.value) onClickPause() else onClickPlay()
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
    VoiceNoteTheme {
        val clickListener = object : UiAudioPlayerClickListener {
            override fun onClickPlay(recording: Recording) {}
            override fun onClickPause() {}
            override fun onSeekTo(position: Float) {}
            override fun onToggleExpandContainer(recordingId: String) {}
            override fun onClickDelete(recordingId: String) {}

        }

        Column {
            RecordingMenuItemPlayer(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                durationText = "02:21",
                progress = remember { mutableLongStateOf(30) },
                isFirstItem = true,
                onClickPlay = { },
                onClickPause = { /* TODO add onclick pause */ },
                isPlaying = remember { mutableStateOf(true) },
                durationFloat = 2000F,
                onClickDelete = {},
                uiAudioPlayerClickListener = clickListener,
            )
            Divider()
            RecordingMenuItemPlayer(
                title = "hej",
                date = "2023-04-01",
                id = "5",
                durationText = "02:21",
                progress = remember { mutableLongStateOf(40) },
                isFirstItem = false,
                onClickPlay = { },
                onClickPause = { /* TODO add onclick pause */ },
                isPlaying = remember { mutableStateOf(false) },
                durationFloat = 2000F,
                onClickDelete = {},
                uiAudioPlayerClickListener = clickListener,
            )
            Divider()
        }
    }
}
