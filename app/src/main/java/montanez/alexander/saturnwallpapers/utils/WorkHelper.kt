package montanez.alexander.saturnwallpapers.utils

import android.content.Context
import androidx.work.*
import montanez.alexander.saturnwallpapers.DailyWallpaperWorker
import montanez.alexander.saturnwallpapers.model.Constants
import java.util.concurrent.TimeUnit

class WorkHelper {
    companion object{
        fun setWorker(context: Context){
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val periodicWorkRequest =
                PeriodicWorkRequestBuilder<DailyWallpaperWorker>(
                    Constants.WORKER_PERIOD, TimeUnit.HOURS
                )
                    .setConstraints(constraints)
                    .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    Constants.WORK_MANAGER_DAILY_ID,
                    ExistingPeriodicWorkPolicy.KEEP,
                    periodicWorkRequest)
        }

        fun stopWorker(context: Context){
            WorkManager.getInstance(context)
                .cancelUniqueWork(Constants.WORK_MANAGER_DAILY_ID)
        }
    }
}