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

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateNoteRepo(notesModel: AllNotesModel) {
        notesDao.updateNotes(notesModel)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteNoteRepo(notesModel: List<AllNotesModel>) {
        notesDao.deleteNotes(notesModel)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addSubRepo(notesModel: AllNotesModel){
        notesDao.addSubscription(notesModel)
    }
}