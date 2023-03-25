package com.larsson.voicenote_android.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.Note
import com.larsson.voicenote_android.ui.NotesList
import com.larsson.voicenote_android.viewmodels.NotesViewModel

enum class ListVariant {
    NOTES,
    RECORDINGS,
}

@Composable
fun ListContent(
    listVariant: ListVariant,
    notes: List<Note>,
    navController: NavController,
    notesViewModel: NotesViewModel
) {
    when (listVariant) {
        ListVariant.NOTES -> { NotesList(notes = notes, navController = navController, notesViewModel = notesViewModel) }
        ListVariant.RECORDINGS -> { RecordingsList() }
    }
}
