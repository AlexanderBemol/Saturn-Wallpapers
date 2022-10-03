package montanez.alexander.saturnwallpapers.utils

import android.content.Context
import android.text.format.DateUtils
import montanez.alexander.saturnwallpapers.model.Constants
import java.text.SimpleDateFormat
import java.util.*

fun Date.getYesterday() : Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return calendar.time
}

fun Date.getParsedDate() : String {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
    return formatter.format(this)
}

fun String.getDateFromString() : Date {
    val formatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
    return formatter.parse(this) ?: Date()
}

fun Date.getReadableString() : String {
    val formatter = SimpleDateFormat(Constants.READABLE_DATE_FORMAT, Locale.US)
    return formatter.format(this)
}

fun Int.resourceToString(context: Context) = context.getText(this).toString()

fun Date.toTimestampFilename() = this.time.toString() + ".JPEG"

fun Date.isTheSameDayAs(date: Date) : Boolean{
    val thisCalendar = Calendar.getInstance().apply { time = this@isTheSameDayAs }
    val objectiveCalendar = Calendar.getInstance().apply { time = date }
    return thisCalendar.get(Calendar.YEAR) == objectiveCalendar.get(Calendar.YEAR) &&
            thisCalendar.get(Calendar.MONTH) == objectiveCalendar.get(Calendar.MONTH) &&
            thisCalendar.get(Calendar.DAY_OF_MONTH) == objectiveCalendar.get(Calendar.DAY_OF_MONTH)
}