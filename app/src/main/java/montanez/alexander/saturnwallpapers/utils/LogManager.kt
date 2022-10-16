package montanez.alexander.saturnwallpapers.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.model.LogData
import montanez.alexander.saturnwallpapers.model.Transactions
import montanez.alexander.saturnwallpapers.repository.ILogDataRepository
import java.util.Date

class LogManager(
    private val logDataRepository: ILogDataRepository
) {
    suspend fun logData(logData: LogData){
        withContext(Dispatchers.IO){
            logDataRepository.insertOneLogData(logData)
            if(logData.transaction.isDebuggable()) Log.d(Constants.APP_DEBUG_TAG,logData.toString())
        }
    }

    suspend fun wasWallpaperSetWithSericeToday(date: Date) : Boolean{
        return withContext(Dispatchers.IO) {
            val list = logDataRepository.getData()
            return@withContext list.count {
                it.dateTime.isTheSameDayAs(date) &&
                        it.transaction == Transactions.WORK_MANAGER_WALLPAPER_ATTEMPT &&
                        it.success
            } > 0
        }
    }
}