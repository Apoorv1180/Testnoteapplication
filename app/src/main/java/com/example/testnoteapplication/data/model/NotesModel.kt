package com.example.testnoteapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "Notes")
data class NotesModel(

    @PrimaryKey()
    var noteId: Int,
    var noteTitle: String,
    var noteDescription: String/*,
    var createdOn: Date,
    var updatedOn: Date*/
    /*var label: String? = null,
    var noteColor: String = "#FFFFFF",
    var isArchived: Boolean = false,
    var ifReminder: String? = null,
    var isTrashed: Boolean = false*/
)