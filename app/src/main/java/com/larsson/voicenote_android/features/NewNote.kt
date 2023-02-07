package com.larsson.voicenote_android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun NewNoteScreen(
    notesViewModel: NotesViewModel,
    id: String
) {
    var textFieldValueContent by remember { mutableStateOf("") }
    var textFieldValueTitle by remember { mutableStateOf("title") }

    NoteView(
        textFieldValueTitle = textFieldValueTitle,
        textFieldValueContent = textFieldValueContent,
        onBackClick = {
            notesViewModel.createNote(textFieldValueTitle, textFieldValueContent)
            notesViewModel.visibilityModifier(homeScreen = true)
        },
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it }

    )
}