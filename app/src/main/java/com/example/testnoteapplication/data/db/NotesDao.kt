package com.example.testnoteapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testnoteapplication.data.model.NotesModel

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes where noteType=:noteType")
    fun getAllNotes(noteType: String): LiveData<List<NotesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotes(notesModel: NotesModel)

    @Query("SELECT * FROM Notes")
    fun getAllTypeNotes(): LiveData<List<NotesModel>>
/*
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNotes(notesModels: List<NotesModel>): Boolean

    @Delete
    fun deleteNotes(notesModels: List<NotesModel>)

    @Query("SELECT * FROM Notes where noteId = :noteId")
    fun getNotes(noteId: Int) : LiveData<NotesModel>*/
}