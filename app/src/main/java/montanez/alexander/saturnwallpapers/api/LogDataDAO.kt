package montanez.alexander.saturnwallpapers.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import montanez.alexander.saturnwallpapers.model.LogData

@Dao
interface LogDataDAO {
    @Query("SELECT * FROM LogData")
    fun getAll(): List<LogData>

    @Insert
    fun insertOne(logData: LogData)

}