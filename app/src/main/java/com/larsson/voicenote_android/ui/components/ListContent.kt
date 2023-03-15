package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import com.larsson.voicenote_android.ui.NotesList

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant
) {
    when (listVariant) {
        ListVariant.NOTES -> { NotesList() }
        ListVariant.RECORDINGS -> { RecordingsList() }
    }
}
