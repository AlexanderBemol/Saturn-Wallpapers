package montanez.alexander.saturnwallpapers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import montanez.alexander.saturnwallpapers.model.Constants
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val periodicWorkRequest =
            PeriodicWorkRequestBuilder<DailyWallpaperWorker>(
                6,TimeUnit.HOURS,
                1,TimeUnit.HOURS
            ).build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                Constants.WORK_MANAGER_DAILY_ID,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest)

    }
}