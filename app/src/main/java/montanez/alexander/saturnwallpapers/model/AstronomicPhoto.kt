package montanez.alexander.saturnwallpapers.model

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import java.util.*

data class AstronomicPhoto(
    val date: Date,
    val explanation: String?,
    @SerializedName("hdurl") val photoHDUrl: String?,
    @SerializedName("media_type") val mediaType: String?,
    val title: String?,
    @SerializedName("copyright") val author: String?,
    @SerializedName("url") val photoRegularUrl: String?,
    var picture: Bitmap?
    )
