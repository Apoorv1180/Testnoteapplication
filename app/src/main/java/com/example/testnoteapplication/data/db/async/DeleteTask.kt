package com.sdsu.noteapp.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AllNotesViewModel

class DeleteTask(var context: Context?, var viewModel: AllNotesViewModel, var notesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.deleteNote(notesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Toast.makeText(context, "Updated to Database", Toast.LENGTH_LONG).show()
        }
    }
}