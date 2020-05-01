package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.NotesModel
import com.example.testnoteapplication.viewmodel.enum.NoteType

class NotesRepository {
    fun getAllNotes(noteType: NoteType): LiveData<List<NotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes(noteType.toString())
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

    fun getAllTypeNotes(): LiveData<List<NotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllTypeNotes();
    }
}