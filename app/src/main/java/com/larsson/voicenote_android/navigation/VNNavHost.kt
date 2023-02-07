
package com.larsson.voicenote_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.larsson.voicenote_android.features.HomeScreen
import org.koin.androidx.compose.get


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.NewNote.route) { HomeScreen(notesViewModel = get()) }
    }
}
