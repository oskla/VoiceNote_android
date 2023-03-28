package com.larsson.voicenote_android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.helpers.DateFormatter
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.NoteView
import com.larsson.voicenote_android.ui.components.RecordingMenu
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import kotlinx.coroutines.CoroutineScope

// TODO maybe state hoist
// TODO on swipe back, do something else, now it's just empty
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NotesViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    noteId: String,
) {
    var selectedNote by remember { mutableStateOf<NoteEntity?>(null) }
    var isDataFetched by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        val note = viewModel.getNoteFromRoomById(id = noteId)
        selectedNote = note
        isDataFetched = true
    }

    if (!isDataFetched) {
        // Show loader
        return
    }

    val title by remember { mutableStateOf(selectedNote!!.noteTitle) }
    val textContent by remember { mutableStateOf(selectedNote!!.noteTxtContent) }

    var textFieldValueContent by remember { mutableStateOf(textContent) }
    var textFieldValueTitle by remember { mutableStateOf(title) }
    var showRecordingMenu by remember { mutableStateOf(false) }

    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, scope = scope)

    ConstraintLayout() {
        val (noteView, bottomBox, menu) = createRefs()

        NoteView(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .constrainAs(noteView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(bottomBox.top)
                },
            onBackClick = {
                // If note has not been change, don't update the note
                if (title != textFieldValueTitle || textContent != textFieldValueContent) {
                    viewModel.updateNoteRoom(title = textFieldValueTitle, txtContent = textFieldValueContent, id = noteId)
                }
                navController.popBackStack()
            },
            textFieldValueContent = textFieldValueContent,
            textFieldValueTitle = textFieldValueTitle,
            onTextChangeTitle = { textFieldValueTitle = it },
            onTextChangeContent = { textFieldValueContent = it },
            date = DateFormatter(selectedNote!!.date).formattedDateTime,
        )
        if (showRecordingMenu) {
            Column(
                modifier = Modifier
                    .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.5f)
                    .constrainAs(menu) {
                        bottom.linkTo(bottomBox.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(bottomBox.top)
                    },

            ) {
                Divider(color = MaterialTheme.colorScheme.background)
                RecordingMenu(
                    noteId = "selectedNote.id",
                    // modifier = Modifier.border(1.dp, Color.Cyan)
                )
            }
        }
        BottomBox(
            modifier = Modifier
                .constrainAs(bottomBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            variant = Variant.RECORDINGS_RECORD,
            onClickLeft = { showRecordingMenu = !showRecordingMenu },
            onClickRight = {
                showRecordingMenu = false
                openBottomSheet.value = true
            },
        )
    }
}
