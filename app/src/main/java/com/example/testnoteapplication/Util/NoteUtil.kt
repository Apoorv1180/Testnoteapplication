package com.example.testnoteapplication.Util

import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.testnoteapplication.R
import java.util.*


class NoteUtil {

    companion object {

        var cal = Calendar.getInstance()
        const val NOTE: String = "NOTE"
        const val LIST = "LIST"
        const val SUB:String = "SUB"

        fun checkInput(editText: EditText): Boolean {
            return editText.text.isNotEmpty()
        }

        fun checkInput(editText: TextView): Boolean {
            return editText.text.isNotEmpty()
        }
    }
}