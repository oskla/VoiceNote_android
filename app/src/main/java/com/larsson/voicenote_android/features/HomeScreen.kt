package com.larsson.voicenote_android.features // ktlint-disable package-name

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.larsson.voicenote_android.data.entity.NoteEntity
import com.larsson.voicenote_android.data.entity.RecordingEntity
import com.larsson.voicenote_android.navigation.Screen
import com.larsson.voicenote_android.ui.components.BottomBox
import com.larsson.voicenote_android.ui.components.BottomSheet
import com.larsson.voicenote_android.ui.components.ListContent
import com.larsson.voicenote_android.ui.components.ListVariant
import com.larsson.voicenote_android.ui.components.TopToggleBar
import com.larsson.voicenote_android.ui.components.Variant
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import com.larsson.voicenote_android.viewmodels.RecordingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    navController: NavController,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
) {
    val notesState = remember { mutableStateOf(emptyList<NoteEntity>()) }
    val recordingsState = remember { mutableStateOf(emptyList<RecordingEntity>()) }

    LaunchedEffect(key1 = bottomSheetState.currentValue) {
        val notes = notesViewModel.getAllNotesFromRoom()
        val recordings = recordingViewModel.getAllRecordingsRoom()
        notesState.value = notes
        recordingsState.value = recordings
    }

    HomeScreenContent(
        notesState = notesState,
        navController = navController,
        notesViewModel = notesViewModel,
        openBottomSheet = openBottomSheet,
        bottomSheetState = bottomSheetState,
        recordingViewModel = recordingViewModel,
        recordingsState = recordingsState,
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    notesViewModel: NotesViewModel,
    recordingViewModel: RecordingViewModel,
    notesState: MutableState<List<NoteEntity>>,
    recordingsState: MutableState<List<RecordingEntity>>,
    navController: NavController,
    modifier: Modifier = Modifier,
    openBottomSheet: MutableState<Boolean>,
    bottomSheetState: SheetState,
) {
    val TAG = "HOME SCREEN"

    val notesListVisible = notesViewModel.notesListVisible

    BottomSheet(openBottomSheet = openBottomSheet, bottomSheetState = bottomSheetState, recordingViewModel = recordingViewModel)

    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
    ) {
        TopToggleBar(modifier = Modifier, viewModel = notesViewModel)

        Box(
            modifier = Modifier
                .weight(1f),
        ) {
            ListContent(
                listVariant = if (notesListVisible) ListVariant.NOTES else ListVariant.RECORDINGS,
                notes = notesState,
                navController = navController,
                recordings = recordingsState,
            )
        }
        BottomBox(
            variant = Variant.NEW_NOTE_RECORD,
            onClickRight = {
                openBottomSheet.value = true
            },
            onClickLeft = { navController.navigate(Screen.NewNote.route) },
        )
    }
}
