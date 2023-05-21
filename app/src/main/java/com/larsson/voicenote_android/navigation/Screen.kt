package com.larsson.voicenote_android.navigation

sealed class Screen(val route: String, var title: String) {
    object Home : Screen("main_screen", "Home")
    object NewNote : Screen("new_note_screen", "New note")
    object EditNote :
        Screen("edit_note_screen", "Edit note")
}
