package com.larsson.voicenote_android.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import java.util.UUID

@Composable
fun NewNoteScreen(
    notesViewModel: NotesViewModel,
    navController: NavController,
) {
    var textFieldValueContent by remember { mutableStateOf("") }
    var textFieldValueTitle by remember { mutableStateOf("title") }

    NoteView(
        textFieldValueTitle = textFieldValueTitle,
        textFieldValueContent = textFieldValueContent,
        onBackClick = {
            notesViewModel.addNoteToRoom(title = textFieldValueTitle, textFieldValueContent, id = UUID.randomUUID().toString())
            notesViewModel.createNote(textFieldValueTitle, textFieldValueContent)
            navController.popBackStack()
        },
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it },
    )
}
