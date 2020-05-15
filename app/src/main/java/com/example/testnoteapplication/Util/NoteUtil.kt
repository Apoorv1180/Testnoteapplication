package com.example.testnoteapplication.Util

import android.widget.EditText
import android.widget.TextView
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class NoteUtil {

    companion object {

        const val NOTE: String = "NOTE"
        const val LIST = "LIST"
        const val SUB: String = "SUB"

        fun checkInput(editText: EditText): Boolean {
            return editText.text.isNotEmpty()
        }

        fun checkInput(editText: TextView): Boolean {
            return editText.text.isNotEmpty()
        }

        fun checkInput(editText: String): Boolean {
            return editText.isNotEmpty()
        }

        fun generateDescription(noteDescription: String): String? {
            var testModel :String =""
            var jsonObject : JsonObject
            jsonObject =  JsonParser().parse(noteDescription).asJsonObject
            val entrySet = jsonObject.entrySet()
            for ((key) in entrySet) {
                if(jsonObject.get(key).asBoolean.equals(true))
                testModel = testModel + "\u2713" + "   "+ key +"\n"
                else
                    testModel = testModel + "\u2717" + "   "+ key +"\n"
            }
            return testModel
        }

        fun generateDescriptionStringList(noteDescription: String): ArrayList<String> {
            var arrayString :ArrayList<String> = ArrayList()
            var jsonObject : JsonObject
            jsonObject =  JsonParser().parse(noteDescription).asJsonObject
            val entrySet = jsonObject.entrySet()
            for ((key) in entrySet) {
                arrayString.add(key)
            }
            return arrayString
        }

        fun convertDateToString(toString: Calendar): String {
            val dueDateStr:String
            val dateFormat = SimpleDateFormat("MM/dd/yyyy")
            dueDateStr = dateFormat.format(toString.time)
        return  dueDateStr
        }

        fun convertDateToTimeInMilli(toString: Calendar): Long {
            val dueDateStr:Long
            dueDateStr = toString.timeInMillis
            return  dueDateStr
        }
    }
}