package com.example.testnoteapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.model.AllNotesModel

class NotesRepository {
    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllNotes(noteType.toString())
    }

    fun addNoteRepo(notesModel: AllNotesModel): LiveData<Long> {
        val data = MutableLiveData<Long>()
        val noteId = NoteAppRoomStartup.database!!.notesDao().addNotes(notesModel)
        data.value = noteId
        return data
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return NoteAppRoomStartup.database!!.notesDao().getAllTypeNotes();
    }
}