package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.NotesModel

class NotesRepository {
    fun getAllNotes(): LiveData<List<NotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes()
    }

   /* fun getNotes(noteId: Int): LiveData<NotesModel> {
        return NoteAppRoomStartup.database!!.notesDao().getNotes(noteId)
    }*/

    fun addNote(notesModel: NotesModel): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
         NoteAppRoomStartup.database!!.notesDao().addNotes(notesModel)
        data.value = true
        return data
    }
}