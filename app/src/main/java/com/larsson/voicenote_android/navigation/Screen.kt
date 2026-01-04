package com.larsson.voicenote_android.navigation

import androidx.navigation3.runtime.NavKey
import com.larsson.voicenote_android.models.NoteId
import kotlinx.serialization.Serializable

sealed class Screen() : NavKey {
    @Serializable
    object Home : Screen()

    @Serializable
    data class EditNote(val noteId: NoteId) : Screen()
}
