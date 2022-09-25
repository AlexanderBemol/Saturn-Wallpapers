package montanez.alexander.saturnwallpapers.utils

import android.content.Context
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