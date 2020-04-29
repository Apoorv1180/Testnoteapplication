package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.NotesModel

class NotesRepository {
    /*fun getAllNotes(): LiveData<List<NotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes()
    }

    fun getNotes(noteId: Int): LiveData<NotesModel> {
        return NoteAppRoomStartup.database!!.notesDao().getNotes(noteId)
    }*/

    fun addNote(notesModel: NotesModel): Boolean {
        return NoteAppRoomStartup.database!!.notesDao().addNotes(notesModel)
    }
}