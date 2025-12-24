package kr.co.roysoft.biblereadingchecker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReadingReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    
    override fun doWork(): Result {
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val channelId = "reading_reminder_channel"
        val channel = NotificationChannel(
            channelId,
            "Reading Reminder",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Daily Bible reading reminder"
        }
        notificationManager.createNotificationChannel(channel)
        
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Daily Bible Reading")
            .setContentText("Time for your daily Scripture reading")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        
        notificationManager.notify(1, notification)
        
        return Result.success()
    }
}

