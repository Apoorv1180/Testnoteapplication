package com.example.testnoteapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "AllNotes")
data class AllNotesModel(

    @PrimaryKey(autoGenerate = true)
    var noteId: Int,
    var noteTitle: String,
    var noteDescription: String,
    var noteType: String,
    var createdOn: String
    /*var updatedOn: Date,
    var noteColor: String = "#FFFFFF",
    var label: String? = null,
    var isArchived: Boolean = false,
    var ifReminder: String? = null,
    var isTrashed: Boolean = false*/


)