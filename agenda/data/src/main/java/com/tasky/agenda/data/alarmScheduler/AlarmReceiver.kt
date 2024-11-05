package com.tasky.agenda.data.alarmScheduler

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.tasky.agenda.data.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra(TITLE) ?: return
        val description = intent.getStringExtra(DESCRIPTION) ?: return
        val id = intent.getStringExtra(ID) ?: return
        if (context != null) {
            postNotification(
                context,
                title,
                description,
                id
            )
        }
    }

    private fun postNotification(
        context: Context,
        title: String,
        desc: String,
        id: String
    ) {

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val pendingIntent = PendingIntent.getActivity(
                context,
                id.hashCode(),
                Intent(context, Class.forName(ACTIVITY_CLASS_NAME)),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val notification = NotificationCompat.Builder(
                context,
                REMINDER_NOTIFICATION_CHANNEL_ID
            )
                .setSmallIcon(R.drawable.tasky_logo)
                .setBadgeIconType(R.drawable.tasky_logo)
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(id.hashCode(), notification)
        }
    }

    companion object {
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val ID = "id"
        const val REMINDER_NOTIFICATION_CHANNEL_ID = "Reminder"
        const val REMINDER_NOTIFICATION_CHANNEL_NAME = "Reminder"
        const val ACTIVITY_CLASS_NAME = "com.tasky.MainActivity"
    }
}
