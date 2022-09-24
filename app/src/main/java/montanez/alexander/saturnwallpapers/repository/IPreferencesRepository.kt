package montanez.alexander.saturnwallpapers.repository

import kotlinx.coroutines.flow.Flow
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper
import montanez.alexander.saturnwallpapers.model.SettingsValues

interface IPreferencesRepository {
    suspend fun wasOnBoardingCompleted() : Flow<Boolean>
    suspend fun getSettings() : Flow<SettingsValues>
    suspend fun setOnBoardingComplete(value: Boolean)
    suspend fun setSettingsQuality(value: QualityOfImages)
    suspend fun setSettingsWallpaperScreen(value: ScreenOfWallpaper)
    suspend fun setServiceEnabled(value: Boolean)
    suspend fun initPreferences()
}