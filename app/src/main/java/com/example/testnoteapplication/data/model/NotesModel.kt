package com.example.testnoteapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Notes")
data class NotesModel(

    @PrimaryKey(autoGenerate = true)
    var noteId: Int,
    val noteTitle: String? = null,
    val noteDescription: String? = null,
    var createdOn: Date,
    var updatedOn: Date,
    val label: String? = null,
    val noteColor: String = "#FFFFFF",
    val isArchived: Boolean = false,
    val ifReminder: String? = null,
    val isTrashed: Boolean = false
)