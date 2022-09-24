package montanez.alexander.saturnwallpapers.repository

import android.graphics.Bitmap
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
interface IMainRepository {
    suspend fun getBitmapOfPhotoOfTheDay() : AstronomicPhoto
    suspend fun getBitmapOfPhotoOfTheDay(imageUrl: String) : Bitmap
}