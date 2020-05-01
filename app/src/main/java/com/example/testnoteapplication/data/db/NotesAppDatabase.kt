package com.example.testnoteapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testnoteapplication.data.model.NotesModel

@Database(entities = [(NotesModel::class)], version = 2)
abstract class NotesAppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

}