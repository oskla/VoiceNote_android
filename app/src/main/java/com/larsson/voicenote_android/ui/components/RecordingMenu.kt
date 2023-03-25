package com.larsson.voicenote_android.ui.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.recordings
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme

@SuppressLint("SuspiciousIndentation")
@Composable
fun RecordingMenu() {

    var selectedRecordingId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        userScrollEnabled = true
    ) {
        itemsIndexed(recordings) { _, recording ->
            val isSelected = (recording.id == selectedRecordingId) // Checks what recording is actually pressed.

                RecordingMenuItem(
                    title = recording.title,
                    date = recording.date,
                    duration = recording.duration,
                    id = recording.id,
                    isPlaying = true, // TODO should come from viewmodel
                    progress = 0.6f, // TODO should come from viewmodel,
                    isSelected = isSelected,
                    onClick = {
                        selectedRecordingId = if (isSelected) null else recording.id
                    }
                )
                Divider(color = MaterialTheme.colorScheme.background)
        }
    }
}

private const val componentName = "Recording Menu Item Player"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
fun RecordingMenuPreview() {
    VoiceNote_androidTheme {
        RecordingMenu()
    }
}
