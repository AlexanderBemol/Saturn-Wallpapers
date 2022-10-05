package montanez.alexander.saturnwallpapers.utils

import android.app.Application
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import montanez.alexander.saturnwallpapers.DailyWallpaperWorker
import montanez.alexander.saturnwallpapers.model.Constants
import java.util.concurrent.TimeUnit

class WorkHelper {
    companion object{
        fun setWorker(context: Context){
            val periodicWorkRequest =
                PeriodicWorkRequestBuilder<DailyWallpaperWorker>(
                    Constants.WORKER_PERIOD, TimeUnit.HOURS
                ).addTag(Constants.WORK_MANAGER_DAILY_TAG).build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    Constants.WORK_MANAGER_DAILY_ID,
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWorkRequest)
        }

        fun stopWorker(context: Context){
            WorkManager.getInstance(context)
                .cancelAllWorkByTag(Constants.WORK_MANAGER_DAILY_TAG)
        }
    }
}