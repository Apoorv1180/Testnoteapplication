package com.example.testnoteapplication.Util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, NotificationService::class.java)
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
        service.putExtra("title", intent.getStringExtra("title"))
        service.putExtra("description", intent.getStringExtra("description"))
        service.putExtra("type", intent.getStringExtra("type"))
        context.startService(service)
    }

}