package com.larsson.voicenote_android.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.larsson.voicenote_android.features.EditNoteScreen
import com.larsson.voicenote_android.features.homescreen.HomeScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val backStack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = Modifier,
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(
                    homeViewModel = koinViewModel(),
                    openBottomSheet = openBottomSheet,
                    bottomSheetState = bottomSheetState,
                    audioPlayerViewModel = koinViewModel(),
                    onNavigateToNote = { noteId ->
                        backStack.add(Screen.EditNote(noteId))
                    },
                )
            }

            entry<Screen.EditNote>(
                metadata = mapOf("extraDataKey" to "extraDataValue")
            ) { key ->
                EditNoteScreen(
                    openBottomSheet = openBottomSheet,
                    bottomSheetState = bottomSheetState,
                    noteId = key.noteId,
                    audioPlayerViewModel = koinViewModel(),
                    editNoteViewModel = koinViewModel(),
                    onBackClick = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}