package com.tasky.agenda.data.alarmScheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        println("Alarm Received")
        val title = intent?.getStringExtra(TITLE) ?: return
        val description = intent.getStringExtra(DESCRIPTION) ?: return
        val id = intent.getStringExtra(ID) ?: return

        println("$title $description $id")
    }

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val ID = "id"
    }
}
