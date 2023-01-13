package com.larsson.voicenote_android


const val NOTE_ARGUMENT_KEY = "title"
const val NOTE_ARGUMENT_KEY2 = "txtContent"
const val NOTE_ARGUMENT_KEY3 = "id"

sealed class Screen(val route: String) {
    object Home:
        Screen("main_screen")

    object NewNote: Screen("new_note_screen?{$NOTE_ARGUMENT_KEY3}")
    object EditNote:
        Screen("edit_note_screen?title={$NOTE_ARGUMENT_KEY}&txtContent={$NOTE_ARGUMENT_KEY2}&id={$NOTE_ARGUMENT_KEY3}")

    fun passId(
        id: String,
    ): String {
        return "new_note_screen?id={$NOTE_ARGUMENT_KEY3}"
    }


    // Optionally pass information
    fun passTitleAndContent(
        title: String = "test",
        txtContent: String = "contentTest",
        id: String,
    ): String {
        return "edit_note_screen?title=$title&txtContent=$txtContent&id=$id"
    }

}


