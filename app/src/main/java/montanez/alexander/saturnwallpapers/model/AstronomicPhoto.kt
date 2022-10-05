package montanez.alexander.saturnwallpapers.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import montanez.alexander.saturnwallpapers.R
import java.util.*
@Entity
data class AstronomicPhoto(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var date: Date,
        var deviceDate: Date = Date(),
        var explanation: String?,
        @SerializedName("hdurl") var photoHDUrl: String?,
        @SerializedName("media_type") var mediaType: String?,
        var title: String?,
        @SerializedName("copyright") var author: String?,
        @SerializedName("url") var photoRegularUrl: String?,
        var picturePath: String = "",
        @Ignore var picture: Bitmap? = null,
        var mediaQuality: QualityOfImages = QualityOfImages.NORMAL_QUALITY,
        var localFilename : String = ""
    ){
        constructor() :
                this(0,Date(),Date(),"","","","","",
                        "","",null,
                        QualityOfImages.NORMAL_QUALITY,"")
}