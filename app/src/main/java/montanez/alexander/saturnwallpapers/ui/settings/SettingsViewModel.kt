package montanez.alexander.saturnwallpapers.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper
import montanez.alexander.saturnwallpapers.model.SettingsValues
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository

class SettingsViewModel(
    private val preferencesRepository: IPreferencesRepository
) : ViewModel() {
    val settingsServiceValues = MutableLiveData<SettingsValues>()

    fun getSettingValues(){
        viewModelScope.launch {
            preferencesRepository.getSettings()
                .collect{settingsServiceValues.value = it}
        }

    }

    fun setIsServiceEnabled(value: Boolean){
        viewModelScope.launch { preferencesRepository.setServiceEnabled(value) }
    }
    fun setQualityOfImages(value: QualityOfImages){
        viewModelScope.launch { preferencesRepository.setSettingsQuality(value) }
    }
    fun setScreenOfWallpaper(value: ScreenOfWallpaper){
        viewModelScope.launch { preferencesRepository.setSettingsWallpaperScreen(value) }
    }
    
}