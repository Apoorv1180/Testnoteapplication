package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.AllNotesModel

class NotesRepository {
    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes(noteType.toString())
    }

    fun addNoteRepo(notesModel: AllNotesModel): LiveData<Boolean> {
        val data = MutableLiveData<Boolean>()
        NoteAppRoomStartup.database!!.notesDao().addNotes(notesModel)
        data.value = true
        return data
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllTypeNotes();
    }
}