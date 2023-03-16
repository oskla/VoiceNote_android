
package com.larsson.voicenote_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.larsson.voicenote_android.di.viewModel
import com.larsson.voicenote_android.features.HomeScreen
import com.larsson.voicenote_android.features.NewNoteScreen
import com.larsson.voicenote_android.ui.EditNoteScreen
import com.larsson.voicenote_android.viewmodels.NotesViewModel
import org.koin.androidx.compose.get

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val notesViewModel = remember { NotesViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                notesViewModel = notesViewModel,
                navController = navController
            )
        }
        composable(Screen.NewNote.route) {
            NewNoteScreen(notesViewModel = notesViewModel, id = "", navController = navController)
        }
        composable(Screen.EditNote.route) { EditNoteScreen(viewModel = get(), navController = navController) }
    }
}
