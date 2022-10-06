package montanez.alexander.saturnwallpapers.repository

import montanez.alexander.saturnwallpapers.api.LogDataDAO
import montanez.alexander.saturnwallpapers.model.LogData

class LogDataRepository(
    private val logDataDAO: LogDataDAO
) : ILogDataRepository {

    override suspend fun insertOneLogData(logData: LogData) {
       logDataDAO.insertOne(logData)
    }

    override suspend fun getData() = logDataDAO.getAll()

}