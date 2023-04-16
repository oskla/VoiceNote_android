package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.ui.NotesList

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant,
    notes: MutableState<List<NoteEntity>>,
    recordings: MutableState<List<RecordingEntity>>,
    navController: NavController,
    onClickPlay: (String) -> Unit
) {
    when (listVariant) {
        ListVariant.NOTES -> {
            NotesList(
                notes = notes,
                navController = navController,
                recordings = recordings
            )
        }
        ListVariant.RECORDINGS -> {
            RecordingsList(
                recordings = recordings,
                onClickPlay = { onClickPlay(it) }
            )
        }
    }
}
