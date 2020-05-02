package com.example.testnoteapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testnoteapplication.data.model.AllNotesModel

@Dao
interface NotesDao {

    @Query("SELECT * FROM AllNotes where noteType=:noteType")
    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotes(notesModel: AllNotesModel)

    @Query("SELECT * FROM AllNotes")
    fun getAllTypeNotes(): LiveData<List<AllNotesModel>>
/*
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotes(notesModels: List<NotesModel>): Boolean

    @Delete
    fun deleteNotes(notesModels: List<NotesModel>)

    @Query("SELECT * FROM Notes where noteId = :noteId")
    fun getNotes(noteId: Int) : LiveData<NotesModel>*/
}
