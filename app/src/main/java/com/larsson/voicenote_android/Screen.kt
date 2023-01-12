package com.larsson.voicenote_android


const val EDIT_NOTE_ARGUMENT_KEY = "title"
const val EDIT_NOTE_ARGUMENT_KEY2 = "txtContent"
const val EDIT_NOTE_ARGUMENT_KEY3 = "id"

sealed class Screen(val route: String) {
    object Home:
        Screen("main_screen")
    object EditNote:
        Screen("edit_note_screen?title={$EDIT_NOTE_ARGUMENT_KEY}&txtContent={$EDIT_NOTE_ARGUMENT_KEY2}&id={$EDIT_NOTE_ARGUMENT_KEY3}")

    // Optionally pass information
    fun passTitleAndContent(
        title: String = "test",
        txtContent: String = "contentTest",
        id: String,
    ): String {
        return "edit_note_screen?title=$title&txtContent=$txtContent&id=$id"
    }

}


