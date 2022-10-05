package montanez.alexander.saturnwallpapers.utils

import androidx.room.TypeConverter
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.Transactions
import java.util.*

class RoomConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun intToTransaction(code: Int): Transactions{
        return Transactions.fromInt(code)
    }

    @TypeConverter
    fun transactionToInt(transaction: Transactions): Int{
        return transaction.getCode()
    }

    @TypeConverter
    fun intToQualityOfImage(code: Int) = QualityOfImages.fromInt(code)

    @TypeConverter
    fun qualityOfImageToInt(qualityOfImages: QualityOfImages) = qualityOfImages.getCode()


}