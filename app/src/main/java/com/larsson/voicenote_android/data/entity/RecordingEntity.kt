package com.larsson.voicenote_android.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey
import com.larsson.voicenote_android.data.repository.Recording

const val RECORDINGS_TABLE = "RECORDINGS_TABLE"

@Entity(
    tableName = RECORDINGS_TABLE,
    // If you set noteId on a recording, that note must exist.
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = ["noteId"], // Column in NoteEntity
        childColumns = ["noteId"], // Column in RecordingEntity
        onDelete = SET_NULL // When note deleted, keep recording but set noteId = null
    )]
)
data class RecordingEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo val recordingId: String,
    @ColumnInfo(name = "recording_number")
    val recordingNumber: Int,
    @ColumnInfo(name = "recording_title")
    val recordingTitle: String?,
    @ColumnInfo(name = "recording_link")
    val recordingLink: String,
    @ColumnInfo(name = "recording_date")
    val recordingDate: String,
    @ColumnInfo(name = "recording_duration")
    val recordingDuration: String,
    @ColumnInfo(name = "noteId")
    val noteId: String?
)

fun List<RecordingEntity>.toRecordings(): List<Recording> {
    return map { recordingEntity ->
        Recording(
            userTitle = recordingEntity.recordingTitle,
            link = recordingEntity.recordingLink,
            date = recordingEntity.recordingDate,
            duration = recordingEntity.recordingDuration,
            id = recordingEntity.recordingId,
            noteId = recordingEntity.noteId,
            recordingNumber = recordingEntity.recordingNumber,
        )
    }
}

fun Recording.toRecordingEntity(): RecordingEntity {
    return RecordingEntity(
        recordingId = id,
        recordingNumber = recordingNumber,
        recordingTitle = userTitle,
        recordingLink = link,
        recordingDate = date,
        recordingDuration = duration,
        noteId = noteId
    )
}