package montanez.alexander.saturnwallpapers.ui.home

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.model.*
import montanez.alexander.saturnwallpapers.repository.*
import montanez.alexander.saturnwallpapers.utils.*
import java.util.*

class HomeViewModel(
    private val wallpaperHelper: WallpaperHelper,
    private val preferencesRepository: IPreferencesRepository,
    private val logManager: LogManager,
    private val filesRepository: IFilesRepository,
    private val astronomicPhotoRepository: IAstronomicPhotoRepository,
    application: Application
) : AndroidViewModel(application) {

    val astronomicLiveData = SingleLiveEvent<AstronomicPhoto>()
    val eventStateLiveData = SingleLiveEvent<HomeEventState>()
    val noInternetConnectionState = SingleLiveEvent<Boolean>()

    var bitmapWallpaper: Bitmap? = null

    fun updateSpecificWallpaper(screenOfWallpaper: ScreenOfWallpaper){
        CoroutineScope(Dispatchers.IO).launch{
            var success = false
            var observation = ""
            try{
                val bitmap = bitmapWallpaper
                if (bitmap != null){
                    wallpaperHelper.changeWallpaper(
                        getApplication<Application>().applicationContext,
                        bitmap,
                        screenOfWallpaper
                    )
                    eventStateLiveData.postValue(HomeEventState.WALLPAPER_SET)
                    success = true
                } else {
                    eventStateLiveData.postValue(HomeEventState.ERROR)
                }
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
                observation = e.toString()
            }
            logManager.logData(
                LogData(
                id = 0,
                success = success,
                observations = observation,
                transaction = when(screenOfWallpaper){
                    ScreenOfWallpaper.HOME_SCREEN -> Transactions.HOME_WALLPAPER_CHANGED_MANUALLY
                    ScreenOfWallpaper.LOCK_SCREEN -> Transactions.LOCK_SCREEN_WALLPAPER_CHANGED_MANUALLY
                    else -> Transactions.SYSTEM_WALLPAPER_CHANGED_MANUALLY
                },
                className = HomeViewModel::class.simpleName.toString()
            ))
        }

    }

    fun downloadPhoto(){
        CoroutineScope(Dispatchers.IO).launch{
            var success = false
            var observation = ""
            try{
                val bitmap = bitmapWallpaper
                if (bitmap != null){
                    filesRepository.savePhotoToStorage(bitmap,Date().toTimestampFilename())
                    eventStateLiveData.postValue(HomeEventState.WALLPAPER_SAVED)
                    success = true
                } else {
                    eventStateLiveData.postValue(HomeEventState.ERROR)
                }
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
                observation = e.toString()
            }
            logManager.logData(
                LogData(
                    id = 0,
                    success = success,
                    observations = observation,
                    transaction = Transactions.WALLPAPER_DOWNLOADED,
                    className = HomeViewModel::class.simpleName.toString()
                ))
        }
    }

    fun getAstronomicPhotoOfTheDay(){
        CoroutineScope(Dispatchers.IO).launch{
            preferencesRepository
                .getSettings()
                .collect{
                    val astronomicPhotoData =
                        astronomicPhotoRepository.getAstronomicPhoto(Date(),it.qualityOfImages)
                    if(astronomicPhotoData is TaskResult.Success){
                        val data = astronomicPhotoData.data
                        bitmapWallpaper = data.picture
                        astronomicLiveData.postValue(data)
                    } else if (astronomicPhotoData is TaskResult.Error){
                        //only error
                        if(astronomicPhotoData.e is PhoneNetworkException){
                            noInternetConnectionState.postValue(true)
                        }
                    }
                }
        }
    }

    fun starWorker(){
        CoroutineScope(Dispatchers.IO).launch{
            preferencesRepository
                .getSettings()
                .collect{
                    if(it.isServiceEnabled){
                       WorkHelper.setWorker(getApplication<Application>().applicationContext)
                    } else {
                       WorkHelper.stopWorker(getApplication<Application>().applicationContext)
                    }
                }
        }

    }

}