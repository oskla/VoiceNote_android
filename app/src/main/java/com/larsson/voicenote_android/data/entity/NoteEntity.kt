package com.larsson.voicenote_android.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTES_TABLE = "NOTES_TABLE"

@Entity(tableName = NOTES_TABLE)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int,
    @ColumnInfo(name = "note_title")
    val noteTitle: String,
    @ColumnInfo(name = "note_desc")
    val noteTxtContent: String,
)


