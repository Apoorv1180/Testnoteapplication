package com.example.testnoteapplication.data.model

import android.provider.CalendarContract
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date

@Entity(tableName = "AllNotes")
data class AllNotesModel(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int,
    var noteTitle: String,
    var noteDescription: String,
    var noteType: String,
    var createdOn: String,
    var expiryDate: String,
    var card_color: Int,
    var sub_icon : Int

) : Serializable