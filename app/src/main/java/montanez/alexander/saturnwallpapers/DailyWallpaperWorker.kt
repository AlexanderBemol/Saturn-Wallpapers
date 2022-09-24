package montanez.alexander.saturnwallpapers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.Transactions
import montanez.alexander.saturnwallpapers.repository.ILogDataRepository
import montanez.alexander.saturnwallpapers.repository.IMainRepository
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class DailyWallpaperWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {
    private val mainRepository: IMainRepository by inject()
    private val preferencesRepository: IPreferencesRepository by inject()
    private val wallpaperHelper: WallpaperHelper by inject()
    private val logDataRepository: ILogDataRepository by inject()

    override suspend fun doWork(): Result {
        val astronomicPhotoOfTheDay = mainRepository.getBitmapOfPhotoOfTheDay()
        var result = Result.success()
        withContext(Dispatchers.IO){
            preferencesRepository
                .getSettings()
                .catch { result = Result.retry() }
                .collect{
                    val pictureURL : String =
                        if(it.qualityOfImages == QualityOfImages.HIGH_QUALITY)
                            astronomicPhotoOfTheDay.photoHDUrl.toString()
                        else astronomicPhotoOfTheDay.photoRegularUrl.toString()

                    astronomicPhotoOfTheDay.picture = mainRepository.getBitmapOfPhotoOfTheDay(pictureURL)
                    result = try {
                        wallpaperHelper.changeWallpaper(applicationContext, astronomicPhotoOfTheDay.picture!!,it.screenOfWallpaper)
                        Result.success()
                    } catch (e : Exception){
                        Result.retry()
                    }
                }

            val logData = LogData(
                id = 0,
                transaction = Transactions.WORK_MANAGER_WALLPAPER_ATTEMPT,
                result = when(result){
                    is Result.Success -> 1
                    is Result.Failure -> 2
                    else -> 3
                },
                error = result.outputData.toString(),
                dateTime = Date()
            )
            logDataRepository.insertOneLogData(logData)

        }

        return result
    }

}