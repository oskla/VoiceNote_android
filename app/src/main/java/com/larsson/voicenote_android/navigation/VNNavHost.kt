
package com.larsson.voicenote_android.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.features.HomeScreen
import com.larsson.voicenote_android.features.NewNoteScreen
import com.larsson.voicenote_android.ui.EditNoteScreen
import org.koin.androidx.compose.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                notesViewModel = get(),
                navController = navController,
                openBottomSheet = openBottomSheet,
                bottomSheetState = bottomSheetState,
            )
        }
        composable(Screen.NewNote.route) {
            NewNoteScreen(notesViewModel = get(), navController = navController)
        }
        composable(
            route = "${Screen.EditNote.route}/{noteId}",
        ) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getString("noteId")
            if (noteId != null) {
                EditNoteScreen(
                    viewModel = get(),
                    navController = navController,
                    openBottomSheet = openBottomSheet,
                    bottomSheetState = bottomSheetState,
                    noteId = noteId,
                )
            }
        }
    }
}
