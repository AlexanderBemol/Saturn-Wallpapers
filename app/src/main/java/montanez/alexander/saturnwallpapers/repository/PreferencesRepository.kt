package montanez.alexander.saturnwallpapers.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper
import montanez.alexander.saturnwallpapers.model.SettingsValues

class PreferencesRepository(private val context: Context) : IPreferencesRepository {
    private val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_NAME)

    override suspend fun wasOnBoardingCompleted(): Flow<Boolean> {
       return context.preferencesDataStore.data.map {
            it[PreferencesKeys.WAS_ON_BOARDING_COMPLETED] ?: false
       }
    }

    override suspend fun getSettings(): Flow<SettingsValues> {
        return context.preferencesDataStore.data.map {
            val qualityCode = it[PreferencesKeys.SETTINGS_QUALITY]
            val screenCode = it[PreferencesKeys.SETTINGS_SCREEN]
            SettingsValues(
                isServiceEnabled =  it[PreferencesKeys.SETTINGS_SERVICE] ?: false,
                qualityOfImages = QualityOfImages.fromInt(if(qualityCode is Int) qualityCode else 0),
                screenOfWallpaper = ScreenOfWallpaper.fromInt(if(screenCode is Int) screenCode else 0)
            )

        }
    }

    override suspend fun setOnBoardingComplete(value: Boolean) {
        context.preferencesDataStore.edit { it[PreferencesKeys.WAS_ON_BOARDING_COMPLETED] = value }
    }

    override suspend fun setSettingsQuality(value: QualityOfImages) {
        context.preferencesDataStore.edit { it[PreferencesKeys.SETTINGS_QUALITY] = value.getCode() }
    }

    override suspend fun setSettingsWallpaperScreen(value: ScreenOfWallpaper) {
        context.preferencesDataStore.edit { it[PreferencesKeys.SETTINGS_SCREEN] = value.getCode() }
    }

    override suspend fun setServiceEnabled(value: Boolean) {
        context.preferencesDataStore.edit { it[PreferencesKeys.SETTINGS_SERVICE] = value }
    }

    override suspend fun initPreferences() {
        setServiceEnabled(true)
        setSettingsQuality(QualityOfImages.NORMAL_QUALITY)
        setSettingsWallpaperScreen(ScreenOfWallpaper.BOTH_SCREENS)
    }
}

object PreferencesKeys {
    val WAS_ON_BOARDING_COMPLETED = booleanPreferencesKey(Constants.KEY_ON_BOARDING_COMPLETED)
    val SETTINGS_QUALITY = intPreferencesKey(Constants.KEY_SETTINGS_QUALITY)
    val SETTINGS_SCREEN = intPreferencesKey(Constants.KEY_SETTINGS_SCREEN)
    val SETTINGS_SERVICE = booleanPreferencesKey(Constants.KEY_SETTINGS_SERVICE_ENABLED)
}