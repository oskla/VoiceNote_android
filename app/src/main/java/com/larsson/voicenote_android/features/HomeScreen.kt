package com.larsson.voicenote_android.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.larsson.voicenote_android.data.getUUID
import com.larsson.voicenote_android.ui.NotesList
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.TopToggleBar
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel
) {
    val getAllNotes = notesViewModel.getAllNotes()
    val newNoteId = getUUID()

    val newNoteVisible = notesViewModel.newNoteVisible
    val notesListVisible = notesViewModel.notesListVisible
    val bottomBoxVisible = notesViewModel.bottomBoxVisible
    val topToggleBar = notesViewModel.topToggleBarVisible

    Column() {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            if (topToggleBar) {
                TopToggleBar()
            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                if (newNoteVisible) {
                    NewNoteScreen(notesViewModel, newNoteId)
                }
                if (notesListVisible) {
                    NotesList()
                }
            }
            if (bottomBoxVisible) {
                BottomBox(newNoteId, notesViewModel)
            }
        }
    }
}