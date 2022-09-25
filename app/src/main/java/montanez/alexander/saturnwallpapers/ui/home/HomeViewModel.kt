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
import montanez.alexander.saturnwallpapers.repository.ILogDataRepository
import montanez.alexander.saturnwallpapers.repository.IMainRepository
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import java.util.*

class HomeViewModel(
    private val mainRepository: IMainRepository,
    private val wallpaperHelper: WallpaperHelper,
    private val preferencesRepository: IPreferencesRepository,
    private val logDataRepository: ILogDataRepository,
    application: Application
) : AndroidViewModel(application) {

    val astronomicPhoto = MutableLiveData<AstronomicPhoto>()

    private val _eventState = MutableSharedFlow<HomeEventState>(0)
    val eventState: SharedFlow<HomeEventState> get() = _eventState

    private val _astronomicData = MutableSharedFlow<AstronomicPhoto>(0)
    val astronomicData: SharedFlow<AstronomicPhoto> get() = _astronomicData

    fun getCurrentAstronomicPhotoOfTheDay(){
        viewModelScope.launch {
            val astronomicPhotoOfTheDay = mainRepository.getBitmapOfPhotoOfTheDay()
            preferencesRepository.getSettings().collect{
                val pictureURL : String =
                    if(it.qualityOfImages == QualityOfImages.HIGH_QUALITY)
                        astronomicPhotoOfTheDay.photoHDUrl.toString()
                    else astronomicPhotoOfTheDay.photoRegularUrl.toString()

                astronomicPhotoOfTheDay.picture =
                    mainRepository.getBitmapOfPhotoOfTheDay(pictureURL)

                _astronomicData.emit(astronomicPhotoOfTheDay)
            }
        }
    }

    fun updateWallpaper(newWallpaper: Bitmap){
        CoroutineScope(Dispatchers.IO).launch{
            preferencesRepository.getSettings().collect{
                wallpaperHelper.changeWallpaper(
                    getApplication<Application>().applicationContext,
                    newWallpaper,
                    it.screenOfWallpaper
                )
                val logData = LogData(
                    id = 0,
                    transaction = Transactions.WALLPAPER_CHANGED_MANUALLY,
                    result = 1,
                    error = "",
                    dateTime = Date()
                )
                logDataRepository.insertOneLogData(logData)
            }
        }
    }

    fun updateSpecificWallpaper(screenOfWallpaper: ScreenOfWallpaper){
        viewModelScope.launch {
            val astronomicPhotoOfTheDay = mainRepository.getBitmapOfPhotoOfTheDay()
            preferencesRepository.getSettings().collect {
                val pictureURL: String =
                    if (it.qualityOfImages == QualityOfImages.HIGH_QUALITY)
                        astronomicPhotoOfTheDay.photoHDUrl.toString()
                    else astronomicPhotoOfTheDay.photoRegularUrl.toString()

                astronomicPhotoOfTheDay.picture =
                    mainRepository.getBitmapOfPhotoOfTheDay(pictureURL)

                wallpaperHelper.changeWallpaper(
                    getApplication<Application>().applicationContext,
                    astronomicPhotoOfTheDay.picture!!,
                    screenOfWallpaper
                )

                //eventSuccess.value = HomeEventState.WALLPAPER_SET
                _eventState.emit(HomeEventState.WALLPAPER_SET)

                /*
                logDataRepository.insertOneLogData(LogData(
                    id = 0,
                    transaction = Transactions.WALLPAPER_CHANGED_MANUALLY,
                    result = 1,
                    error = "",
                    dateTime = Date()
                ))

                 */
            }
        }

    }

}