package montanez.alexander.saturnwallpapers.ui.home

import android.app.Application
import android.graphics.Bitmap
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

    val astronomicLiveData = MutableLiveData<AstronomicPhoto>()
    val eventStateLiveData = MutableLiveData<HomeEventState>()

    fun updateSpecificWallpaper(wallpaper: Bitmap, screenOfWallpaper: ScreenOfWallpaper){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                wallpaperHelper.changeWallpaper(
                    getApplication<Application>().applicationContext,
                    wallpaper,
                    screenOfWallpaper
                )
                eventStateLiveData.postValue(HomeEventState.WALLPAPER_SET)
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
            }
        }

    }

    fun downloadPhoto(wallpaper: Bitmap){
        CoroutineScope(Dispatchers.IO).launch{
            try{
                filesRepository.savePhotoToStorage(wallpaper,Date().toTimestampFilename())
                eventStateLiveData.postValue(HomeEventState.WALLPAPER_SAVED)
            } catch (e : Exception){
                eventStateLiveData.postValue(HomeEventState.ERROR)
            }
        }
    }

    fun getAstronomicPhotoOfTheDay(){
        CoroutineScope(Dispatchers.IO).launch{
            val astronomicPhotoData =
                astronomicPhotoRepository.getAstronomicPhoto(Date(),QualityOfImages.NORMAL_QUALITY)
            if(astronomicPhotoData is TaskResult.Success){
                val data = astronomicPhotoData.data
                astronomicLiveData.postValue(data)
            }

        }
    }

}