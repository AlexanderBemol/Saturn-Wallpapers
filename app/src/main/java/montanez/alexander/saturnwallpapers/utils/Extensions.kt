package montanez.alexander.saturnwallpapers.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import android.widget.ImageView
import montanez.alexander.saturnwallpapers.model.Constants
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
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
    val formatter = SimpleDateFormat(Constants.READABLE_DATE_FORMAT, Locale.getDefault())
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

fun Bitmap.getWithFixedSize() : Bitmap{
    val maxW = 1080 * 2
    val maxH = 2280 * 2
    var w = this.width
    var h = this.height
    if(w == h && w >= maxW && h >= maxH) {
        w = maxH
        h = maxH
    } else if (w > h && w > maxW){
        h = (h*maxW)/w
        w = maxW
    } else if (h > w && h > maxW){
        w = (w*maxH)/h
        h = maxH
    }

    val scaledBitmap = Bitmap.createScaledBitmap(this,w,h,true)

    val maxSize = 8000000 //10MB
    val actualSize = scaledBitmap.allocationByteCount

    return if(actualSize <= maxSize) scaledBitmap
    else{
        val quality = ((maxSize.toFloat()/actualSize.toFloat()) * 100).toInt()
        val output = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG,quality,output)
        BitmapFactory.decodeStream(ByteArrayInputStream(output.toByteArray()))
    }
}