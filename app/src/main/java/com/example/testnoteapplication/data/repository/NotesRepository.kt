package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.testnoteapplication.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.NotesModel

class NotesRepository {
    fun getAllNotes(): LiveData<List<NotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes()
    }

    fun getNotes(noteId: Int): LiveData<NotesModel> {
        return NoteAppRoomStartup.database!!.notesDao().getNotes(noteId)
    }
}