package com.larsson.voicenote_android.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.PlayerState
import com.larsson.voicenote_android.data.repository.Recording
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@Composable
fun RecordingMenu(
    modifier: Modifier = Modifier,
    recordings: List<Recording>,
    onClickPlay: (String) -> Unit,
    onClickPause: () -> Unit,
    playerState: PlayerState,
    onClickContainer: () -> Unit,
    currentPosition: Int,
    seekTo: (Float) -> Unit,

    ) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.Transparent),
        userScrollEnabled = true,
    ) {
        if (recordings.isEmpty()) {
            item {
                RecordingMenuItem( // TODO Clean this up, maybe cleanest to either make a separate component for this, or make a different state: "isEmpty".
                    title = "You have no recordings yet",
                    date = "",
                    durationText = "",
                    id = "",
                    progress = 0,
                    isSelected = false,
                    onClickContainer = {},
                    isFirstItem = true,
                    onClickPlay = {},
                    onClickPause = onClickPause,
                    isPlaying = false,
                    seekTo = {},
                )
            }
        }
    }
    if (recordings.isNotEmpty()) {
        RecordingsList(
            recordings = recordings,
            onClickPlay = onClickPlay,
            onClickPause = onClickPause,
            playerState = playerState,
            onClickContainer = onClickContainer,
            currentPosition = currentPosition,
            seekTo = seekTo,
            isMenu = true,
        )
    }
}

private const val componentName = "Recording Menu Item Player"

@SuppressLint("UnrememberedMutableState")
@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuPreview() {
    VoiceNote_androidTheme {
        RecordingMenu(
            recordings = listOf(),
            onClickPlay = {},
            onClickPause = {},
            playerState = PlayerState.Paused,
            onClickContainer = {},
            seekTo = {},
            currentPosition = 0,
        )
    }
}
