package com.larsson.voicenote_android.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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

@Composable
fun RecordingsList() {
    var selectedRecordingId by remember { mutableStateOf<String?>(null) }

    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp), userScrollEnabled = true) {
        itemsIndexed(recordings) { _, recording ->
            val isSelected =
                (recording.id == selectedRecordingId) // Checks what recording is actually pressed.

            Box() {
                RecordingMenuItem(
                    color = MaterialTheme.colorScheme.background,
                    title = recording.title,
                    date = recording.date,
                    id = recording.id,
                    duration = recording.duration,
                    isSelected = isSelected,
                    onClick = { selectedRecordingId = if (isSelected) null else recording.id },
                )

                // RecordingItem(title = recording.title, date = recording.date, duration = recording.duration, onClick = {}, id = recording.id)
            }
        }
    }
}

private const val componentName = "Recordings List"

@Preview("$componentName (light)", showBackground = true)
@Preview("$componentName (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("$componentName (big font)", fontScale = 1.5f, showBackground = true)
@Preview("$componentName (large screen)", device = Devices.PIXEL_C)
@Composable
private fun PreviewComponent() {
    VoiceNote_androidTheme {
        RecordingsList()
    }
}
