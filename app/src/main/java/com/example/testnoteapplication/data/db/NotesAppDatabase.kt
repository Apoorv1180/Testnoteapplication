package com.example.testnoteapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testnoteapplication.data.model.AllNotesModel

@Database(entities = [(AllNotesModel::class)], version = 1)
abstract class NotesAppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

}