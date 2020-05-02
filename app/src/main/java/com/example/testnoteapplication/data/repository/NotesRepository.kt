package com.example.testnoteapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.testnoteapplication.Base.NoteAppRoomStartup
import com.example.testnoteapplication.data.db.NotesAppDatabase
import com.example.testnoteapplication.data.model.AllNotesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class NotesRepository (application: Application)
    : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    init {
        NoteAppRoomStartup.database =
            Room.databaseBuilder(application, NotesAppDatabase::class.java, "notes_db")
                .fallbackToDestructiveMigration().build()
    }


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