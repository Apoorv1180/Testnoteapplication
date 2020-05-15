package com.example.testnoteapplication.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.AllSubscriptionViewModel

class DeleteSubscriptionTask(
    var context: Context?,
    var viewModel: AllSubscriptionViewModel,
    var notesModel: AllNotesModel
) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.deleteNote(notesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show()
        }
    }
}