package com.larsson.voicenote_android.navigation

const val NOTE_ARGUMENT_KEY = "title"
const val NOTE_ARGUMENT_KEY2 = "txtContent"
const val NOTE_ARGUMENT_KEY3 = "id"

sealed class Screen(val route: String, var title: String) {
    object Home : Screen("main_screen", "Home")
    object NewNote : Screen("new_note_screen", "New note")
    object EditNote :
        Screen("edit_note_screen", "Edit note")

    fun passId(
        id: String
    ): String {
        return "new_note_screen?id={$NOTE_ARGUMENT_KEY3}"
    }

    // Optionally pass information
    fun passTitleAndContent(
        title: String = "test",
        txtContent: String = "contentTest",
        id: String
    ): String {
        return "edit_note_screen?title=$title&txtContent=$txtContent&id=$id"
    }
}
