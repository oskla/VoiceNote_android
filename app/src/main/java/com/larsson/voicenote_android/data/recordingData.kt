package com.larsson.voicenote_android.data

import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate

data class Recording(
    val id: String = Note.generateId(),
    var title: String,
    var date: String,
    var duration: String,
    var recording: String, // TODO - change this later to actual recording? Or should this just be information?
    var belongsToNoteId: String? = null // TODO - tie this to the note it belongs to, or if it doesn't belong to a note at all
)

var recording1 = Recording("0", "Recording 1", LocalDate.now().toString(), "01:34", "Recordingzz")
var recording2 = Recording("1", "Recording 2", LocalDate.now().minusDays(1).toString(), "02:21", "Recordingzz")
var recording3 = Recording("2", "Recording 3", LocalDate.now().minusDays(1).toString(), "00:24", "Recordingzz")
var recording4 = Recording("3", "Recording 4", LocalDate.now().minusDays(2).toString(), "12:14", "Recordingzz")
var recording5 = Recording("4", "Recording 5", LocalDate.now().minusDays(2).toString(), "03:02", "Recordingzz")
var recording6 = Recording("5", "Recording 6", LocalDate.now().minusDays(2).toString(), "03:02", "Recordingzz")
var recording7 = Recording("6", "Recording 7", LocalDate.now().minusDays(3).toString(), "03:02", "Recordingzz")
var recording8 = Recording("7", "Recording 8", LocalDate.now().minusDays(4).toString(), "03:02", "Recordingzz")
var recording9 = Recording("8", "Recording 9", LocalDate.now().minusDays(6).toString(), "03:02", "Recordingzz")

val recordings = mutableStateListOf(recording1, recording2, recording3, recording4, recording5, recording6, recording7, recording8, recording9)
