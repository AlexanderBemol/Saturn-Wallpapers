package montanez.alexander.saturnwallpapers.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.repository.ILogDataRepository

class LogManager(
    private val logDataRepository: ILogDataRepository
) {
    suspend fun logData(logData: LogData){
        withContext(Dispatchers.IO){
            logDataRepository.insertOneLogData(logData)
            if(logData.transaction.isDebuggable()) Log.d(Constants.APP_DEBUG_TAG,logData.toString())
        }
    }
}