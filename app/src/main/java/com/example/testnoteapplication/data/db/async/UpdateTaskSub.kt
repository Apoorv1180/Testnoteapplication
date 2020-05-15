package com.example.testnoteapplication.data.db.async

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.testnoteapplication.data.model.AllNotesModel
import com.example.testnoteapplication.viewmodel.EditNoteViewModel
import com.example.testnoteapplication.viewmodel.EditSubscriptionViewModel

class UpdateTaskSub(var context: Context?, var viewModel: EditSubscriptionViewModel, var allNotesModel: AllNotesModel) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        viewModel.updateSubVm(allNotesModel)
        return true
    }

    override fun onPostExecute(bool: Boolean?) {
        if (bool!!) {
            viewModel.setValue(bool)
        }
    }
}