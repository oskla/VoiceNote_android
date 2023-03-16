
package com.larsson.voicenote_android.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.features.HomeScreen
import com.larsson.voicenote_android.features.NewNoteScreen
import com.larsson.voicenote_android.ui.EditNoteScreen
import com.larsson.voicenote_android.viewmodels.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val notesViewModel = remember { NotesViewModel() }
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                notesViewModel = notesViewModel,
                navController = navController,
                openBottomSheet = openBottomSheet,
                bottomSheetState = bottomSheetState,
                scope = scope
            )
        }
        composable(Screen.NewNote.route) {
            NewNoteScreen(notesViewModel = notesViewModel, navController = navController)
        }
        composable(Screen.EditNote.route) {
            EditNoteScreen(
                viewModel = notesViewModel,
                navController = navController,
                openBottomSheet = openBottomSheet,
                bottomSheetState = bottomSheetState,
                scope = scope
            )
        }
    }
}
