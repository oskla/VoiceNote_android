package com.larsson.voicenote_android.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.components.RecordingMenu
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.ui.theme.VoiceNote_androidTheme
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    scope: CoroutineScope
) {
    val selectedNote = viewModel.getSelectedNote()

    val title by remember { mutableStateOf(selectedNote.title) }
    val textContent by remember { mutableStateOf(selectedNote.txtContent) }
    val id by remember { mutableStateOf(selectedNote.id) }

    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }
    var showRecordingMenu by remember { mutableStateOf(false) }

    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, scope = scope)

    Column {
        NoteView(
            modifier = Modifier.weight(1f),
            onBackClick = {
                Log.d("OnBackClick", "id: $id")
                viewModel.saveNote(textFieldValueTitle, textFieldValueContent, id)
                navController.popBackStack()
            },
            textFieldValueContent = textFieldValueContent,
            textFieldValueTitle = textFieldValueTitle,
            onTextChangeTitle = { textFieldValueTitle = it },
            onTextChangeContent = { textFieldValueContent = it }
        )
        if (showRecordingMenu) {
            Column(modifier = Modifier.wrapContentHeight().weight(1f)) {
                Divider(color = MaterialTheme.colorScheme.background)
                RecordingMenu()
            }
        }
        BottomBox(
            variant = Variant.RECORDINGS_RECORD,
            onClickLeft = { showRecordingMenu = !showRecordingMenu },
            onClickRight = {
                showRecordingMenu = false
                openBottomSheet.value = true
            }
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun EditPreview() {
    VoiceNote_androidTheme {
        EditNoteScreen(
            viewModel = NotesViewModel(),
            navController = rememberNavController(),
            bottomSheetState = rememberModalBottomSheetState(),
            openBottomSheet = mutableStateOf(true),
            scope = rememberCoroutineScope()
        )
    }
}
