package montanez.alexander.saturnwallpapers.api

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.utils.RoomConverters

@Database(entities = [LogData::class, AstronomicPhoto::class] , version = 1)
@TypeConverters(RoomConverters::class)
abstract class SaturnDatabase : RoomDatabase() {
    abstract fun logDataDao() : LogDataDAO
    abstract fun astronomicPhotoDao() : IAstronomicPhotoDAO
}