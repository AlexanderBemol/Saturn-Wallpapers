package montanez.alexander.saturnwallpapers.repository

import android.graphics.Bitmap
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.TaskResult
import java.util.*

interface IAstronomicPhotoRepository {
    suspend fun getAstronomicPhoto(deviceDate : Date, qualityOfImages: QualityOfImages) : TaskResult<AstronomicPhoto>

    suspend fun getPicture(pictureUrl: String) : TaskResult<Bitmap>
}