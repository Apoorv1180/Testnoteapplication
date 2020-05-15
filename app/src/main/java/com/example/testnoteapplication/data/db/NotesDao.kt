package com.example.testnoteapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testnoteapplication.data.model.AllNotesModel

@Dao
interface NotesDao {

    @Query("SELECT * FROM AllNotes where noteType=:noteType order by noteId desc")
    fun getAllNotes(noteType: String): LiveData<List<AllNotesModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNotes(notesModel: AllNotesModel):Long

    @Query("SELECT * FROM AllNotes order by noteId desc")
    fun getAllTypeNotes(): LiveData<List<AllNotesModel>>

    @Update
    fun updateNotes(notesModel: AllNotesModel)

    @Delete
    fun deleteNotes(notesModels: AllNotesModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addSubscription(notesModel: AllNotesModel)

    @Update
    fun updateSub(notesModel: AllNotesModel)

    @Update
    fun updateList(notesModel: AllNotesModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun undoNotes(notesModel: AllNotesModel)
}
