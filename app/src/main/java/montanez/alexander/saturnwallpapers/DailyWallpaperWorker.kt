package montanez.alexander.saturnwallpapers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.model.TaskResult
import montanez.alexander.saturnwallpapers.model.Transactions
import montanez.alexander.saturnwallpapers.repository.IAstronomicPhotoRepository
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository
import montanez.alexander.saturnwallpapers.utils.LogManager
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class DailyWallpaperWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {
    private val preferencesRepository: IPreferencesRepository by inject()
    private val wallpaperHelper: WallpaperHelper by inject()
    private val logManager: LogManager by inject()
    private val astronomicPhotoRepository: IAstronomicPhotoRepository by inject()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            preferencesRepository
                .getSettings()
                .catch {
                    logData(false,this.toString())
                    Result.retry()
                }
                .collect {
                    val taskResult = astronomicPhotoRepository
                        .getAstronomicPhoto(Date(), it.qualityOfImages)

                    if (taskResult is TaskResult.Success) {
                        wallpaperHelper.changeWallpaper(
                            applicationContext,
                            taskResult.data.picture!!,
                            it.screenOfWallpaper
                        )
                        logData(true)
                        Result.success()
                    } else if(taskResult is TaskResult.Error) {
                        logData(false,taskResult.e.toString())
                        Result.retry()
                    }
                }
        } catch (e: Exception) {
            logData(false,e.toString())
            Result.retry()
        }
    } as Result

    private suspend fun logData(success: Boolean, observations: String = ""){
        logManager.logData(
            LogData(
                id = 0,
                transaction = Transactions.WORK_MANAGER_WALLPAPER_ATTEMPT,
                className = DailyWallpaperWorker::class.simpleName.toString(),
                success = success,
                observations = observations
            ))
    }
}