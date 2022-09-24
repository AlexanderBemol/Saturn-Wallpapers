package montanez.alexander.saturnwallpapers.repository

import montanez.alexander.saturnwallpapers.model.LogData

interface ILogDataRepository {
    suspend fun insertOneLogData(logData: LogData)
}