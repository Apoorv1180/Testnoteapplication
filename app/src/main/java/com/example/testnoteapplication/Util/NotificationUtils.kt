package com.example.testnoteapplication.Util

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.example.testnoteapplication.data.model.AllNotesModel
import java.util.*

class NotificationUtils {


    fun setNotification(model:AllNotesModel,timeInMilliSeconds: Long, activity: FragmentActivity) {

        //------------  alarm settings start  -----------------//

        if (timeInMilliSeconds > 0) {

            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(activity.applicationContext, AlarmReceiver::class.java) // AlarmReceiver1 = broadcast receiver
            val dateString = when(model.noteType) {
                NoteUtil.NOTE -> model.createdOn
                NoteUtil.SUB -> model.expiryDate
                else -> model.createdOn
            }

            val reminderDateTimeMilliSeconds = NoteUtil.getReminderDateTime(dateString)

            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", reminderDateTimeMilliSeconds)
            alarmIntent.putExtra("title", model.noteTitle)
            alarmIntent.putExtra("description", model.noteDescription)
            alarmIntent.putExtra("type", model.noteType.toString())
            //todo put all your note model extras here

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = reminderDateTimeMilliSeconds

            val pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
        //------------ end of alarm settings  -----------------//
    }
}