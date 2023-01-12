package com.larsson.voicenote_android

import androidx.navigation.NavType
import androidx.navigation.navArgument

/*
interface VNDestination {
    val route: String
}

object HomeDestination : VNDestination {
    override val route = "home_screen"
}

object EditNoteDestination : VNDestination {
    override val route = "edit_note"
    const val editNoteTypeArg = "notes"
    val routeWithArgs = "${route}/${editNoteTypeArg}"
    val arguments = listOf(
        navArgument(editNoteTypeArg) {
            type = NavType.StringType
        }
    )
}



val VNDestinations = listOf(HomeDestination,EditNoteDestination)


*/


/*
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            Home(navController = navController)
        }
        composable(route = Screen.EditNoteScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Philipp"
                    nullable = true
                }
            )
        ) { entry ->
            EditNoteView(name = entry.arguments?.getString("name"), navController = navController)
        }
    }
}*/
