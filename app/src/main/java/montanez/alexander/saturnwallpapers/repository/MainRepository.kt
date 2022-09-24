package montanez.alexander.saturnwallpapers.repository

import android.graphics.Bitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoHelper
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import montanez.alexander.saturnwallpapers.utils.getDateFromString
import montanez.alexander.saturnwallpapers.utils.getParsedDate
import montanez.alexander.saturnwallpapers.utils.getYesterday
import retrofit2.Response
import java.util.*

class MainRepository(
    private val apiHelper: IAstronomicPhotoHelper,
    private val wallpaperHelper: WallpaperHelper
) : IMainRepository {
    override suspend fun getBitmapOfPhotoOfTheDay() : AstronomicPhoto {
        return try {
            var response = apiHelper.getPhotoOfTheDay()
            if (isAValidResponse(response)) response.body()!!
            else {
                val yesterdayDate = Date().getYesterday()
                val parsedDate = yesterdayDate.getParsedDate()
                response = apiHelper.getPhotoOfADay(parsedDate)
                if (isAValidResponse(response))  response.body()!!
                else getDefaultPhoto()
            }
        } catch (e : Exception){
            getDefaultPhoto()
        }
    }

    override suspend fun getBitmapOfPhotoOfTheDay(imageUrl: String) : Bitmap {
        return withContext(Dispatchers.IO) {
            return@withContext wallpaperHelper.getBitmapFromUrl(imageUrl)
        }
    }

    private fun isAValidResponse(response: Response<AstronomicPhoto>) : Boolean{
        if (response.isSuccessful){
            val astronomicPhoto = response.body()!!
            if (astronomicPhoto.mediaType == Constants.MEDIA_TYPE_VIDEO ||
                (astronomicPhoto.photoHDUrl == null && astronomicPhoto.photoRegularUrl == null)
            ){
                return false
            }
            return true
        }
        return false
    }

    private fun getDefaultPhoto() : AstronomicPhoto{
        return AstronomicPhoto(
            date = Constants.DEFAULT_APOD_DATE.getDateFromString(),
            explanation = Constants.DEFAULT_APOD_EXPLANATION,
            author = Constants.DEFAULT_APOD_COPYRIGHT,
            photoHDUrl = Constants.DEFAULT_APOD_HD_URL,
            photoRegularUrl = Constants.DEFAULT_APOD_REGULAR_URL,
            mediaType = Constants.MEDIA_TYPE_IMAGE,
            title = Constants.DEFAULT_APOD_TITLE,
            picture = null
        )
    }
}