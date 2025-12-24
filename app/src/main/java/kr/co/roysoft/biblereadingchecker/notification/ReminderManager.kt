package kr.co.roysoft.biblereadingchecker.notification

import android.content.Context
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit

class ReminderManager(private val context: Context) {
    
    fun scheduleReminder(hour: Int, minute: Int) {
        val workManager = WorkManager.getInstance(context)
        
        // Cancel existing work
        workManager.cancelUniqueWork("reading_reminder")
        
        // Calculate delay until next reminder time
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            
            // If time has passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }
        
        val delay = calendar.timeInMillis - System.currentTimeMillis()
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        
        val workRequest = OneTimeWorkRequestBuilder<ReadingReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag("reading_reminder")
            .build()
        
        workManager.enqueueUniqueWork(
            "reading_reminder",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
        
        // Schedule recurring work for daily reminders
        val periodicWorkRequest = PeriodicWorkRequestBuilder<ReadingReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .addTag("reading_reminder")
            .build()
        
        workManager.enqueueUniquePeriodicWork(
            "reading_reminder_periodic",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
    }
    
    fun cancelReminder() {
        WorkManager.getInstance(context).cancelUniqueWork("reading_reminder")
        WorkManager.getInstance(context).cancelUniqueWork("reading_reminder_periodic")
    }
}

