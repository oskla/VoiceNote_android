package com.larsson.voicenote_android.ui

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    title: String,
    txtContent: String,
    id: String
) {
    var textFieldValueContent by remember { mutableStateOf(txtContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }

    NoteView(
        onBackClick = {
            Log.d("OnBackClick", "id: $id")
            viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
            println("id: $id")
        },
        textFieldValueContent = textFieldValueContent,
        textFieldValueTitle = textFieldValueTitle,
        onTextChangeTitle = { textFieldValueTitle = it },
        onTextChangeContent = { textFieldValueContent = it }
    )
}

@Preview(showBackground = true)
@Composable
fun EditPreview() {
    VoiceNote_androidTheme {
        EditNoteScreen(NotesViewModel(), "Title", "content", "1")
    }
}
