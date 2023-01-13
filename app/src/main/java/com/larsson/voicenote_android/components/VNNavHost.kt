package com.larsson.voicenote_android.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.larsson.voicenote_android.*
import com.larsson.voicenote_android.ui.EditNoteScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    notesViewModel: NotesViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController, notesViewModel)
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(
                navArgument(NOTE_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = "Title"
                },
                navArgument(NOTE_ARGUMENT_KEY2) {
                    type = NavType.StringType
                },
                navArgument(NOTE_ARGUMENT_KEY3) {
                    type = NavType.IntType
                }
            )
        ) {
            val title = it.arguments?.getString(NOTE_ARGUMENT_KEY).toString()
            val txtContent = it.arguments?.getString(NOTE_ARGUMENT_KEY2).toString()
            val id = it.arguments?.getInt(NOTE_ARGUMENT_KEY3).toString()
            EditNoteScreen(notesViewModel, navController, title, txtContent, id,)

        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController, notesViewModel)
        }
    }
}