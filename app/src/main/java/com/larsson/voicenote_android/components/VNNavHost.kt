package com.larsson.voicenote_android.components

import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
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
                navArgument(EDIT_NOTE_ARGUMENT_KEY) {
                    type = NavType.StringType
                    defaultValue = "Title"
                },
                navArgument(EDIT_NOTE_ARGUMENT_KEY2) {
                    type = NavType.StringType
                },
                navArgument(EDIT_NOTE_ARGUMENT_KEY3) {
                    type = NavType.IntType
                }
            )
        ) {

            val title = it.arguments?.getString(EDIT_NOTE_ARGUMENT_KEY).toString()
            val txtContent = it.arguments?.getString(EDIT_NOTE_ARGUMENT_KEY2).toString()
            val id = it.arguments?.getInt(EDIT_NOTE_ARGUMENT_KEY3).toString()
            EditNoteScreen(notesViewModel, navController, title, txtContent, id,)
            Log.d("Args", title)
            Log.d("Args", txtContent)
        }
    }
}