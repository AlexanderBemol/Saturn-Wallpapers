package montanez.alexander.saturnwallpapers.api

import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import retrofit2.Response

interface IAstronomicPhotoHelper {
    suspend fun getPhotoOfTheDay() : Response<AstronomicPhoto>
    suspend fun getPhotoOfADay(date: String) : Response<AstronomicPhoto>
}