package com.example.testnoteapplication.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.db.NotesDao
import com.example.testnoteapplication.data.model.AllNotesModel

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>> {
        return notesDao.getAllNotes(noteType.toString())
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNoteRepo(notesModel: AllNotesModel) {
        notesDao.addNotes(notesModel)
    }

    fun getAllTypeNotes(): LiveData<List<AllNotesModel>> {
        return notesDao.getAllTypeNotes()
    }
}