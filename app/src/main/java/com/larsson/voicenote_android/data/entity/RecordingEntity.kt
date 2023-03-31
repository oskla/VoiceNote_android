package com.larsson.voicenote_android.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val RECORDINGS_TABLE = "RECORDINGS_TABLE"

@Entity(tableName = RECORDINGS_TABLE)
data class RecordingEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo val recordingId: String,
    @ColumnInfo(name = "recording_title")
    val recordingTitle: String,
    @ColumnInfo(name = "recording_link")
    val recordingLink: String,
    @ColumnInfo(name = "recording_date")
    val recordingDate: String,
    @ColumnInfo(name = "recording_duration")
    val recordingDuration: String,
    @ColumnInfo(name = "noteId")
    val noteId: String
)

