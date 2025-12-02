package com.larsson.voicenote_android.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class HomeNavigation() : NavKey {
    @Serializable
    object NotesList : Screen()

    @Serializable
    object RecordingsList : Screen()
}