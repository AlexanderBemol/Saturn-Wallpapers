package montanez.alexander.saturnwallpapers.ui.home

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.model.*
import montanez.alexander.saturnwallpapers.repository.*
import montanez.alexander.saturnwallpapers.utils.SingleLiveEvent
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import montanez.alexander.saturnwallpapers.utils.toTimestampFilename
import java.util.*

class HomeViewModel(
    private val wallpaperHelper: WallpaperHelper,
    private val preferencesRepository: IPreferencesRepository,
    private val logDataRepository: ILogDataRepository,
    private val filesRepository: IFilesRepository,
    private val astronomicPhotoRepository: IAstronomicPhotoRepository,
    application: Application
) : AndroidViewModel(application) {

    val astronomicLiveData = SingleLiveEvent<AstronomicPhoto>()
    val eventStateLiveData = SingleLiveEvent<HomeEventState>()

    var bitmapWallpaper: Bitmap? = null

    fun updateSpecificWallpaper(screenOfWallpaper: ScreenOfWallpaper){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val bitmap = bitmapWallpaper
                if (bitmap != null){
                    wallpaperHelper.changeWallpaper(
                        getApplication<Application>().applicationContext,
                        bitmap,
                        screenOfWallpaper
                    )
                    eventStateLiveData.postValue(HomeEventState.WALLPAPER_SET)
                } else {
                    eventStateLiveData.postValue(HomeEventState.ERROR)
                }
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
            }
        }

    }

    fun downloadPhoto(){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val bitmap = bitmapWallpaper
                if (bitmap != null){
                    filesRepository.savePhotoToStorage(bitmap,Date().toTimestampFilename())
                    eventStateLiveData.postValue(HomeEventState.WALLPAPER_SAVED)
                } else {
                    eventStateLiveData.postValue(HomeEventState.ERROR)
                }
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
            }
        }
    }

    fun getAstronomicPhotoOfTheDay(){
        Log.d(Constants.APP_DEBUG_TAG,"get function")
        CoroutineScope(Dispatchers.IO).launch{
            val astronomicPhotoData =
                astronomicPhotoRepository.getAstronomicPhoto(Date(),QualityOfImages.NORMAL_QUALITY)
            if(astronomicPhotoData is TaskResult.Success){
                val data = astronomicPhotoData.data
                bitmapWallpaper = data.picture
                astronomicLiveData.postValue(data)
            }

        }
    }

}