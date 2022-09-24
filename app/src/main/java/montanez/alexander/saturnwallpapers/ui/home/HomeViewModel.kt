package montanez.alexander.saturnwallpapers.ui.home

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.Transactions
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

                astronomicPhoto.value = astronomicPhotoOfTheDay
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

}