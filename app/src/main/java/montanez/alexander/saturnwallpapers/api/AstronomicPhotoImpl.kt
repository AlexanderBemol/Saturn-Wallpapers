package montanez.alexander.saturnwallpapers.api

import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import retrofit2.Response

class AstronomicPhotoImpl(
    private val apiService: IAstronomicPhotosService,
    private val apiKey: String
) : IAstronomicPhotoHelper {
    override suspend fun getPhotoOfTheDay(): Response<AstronomicPhoto> =
        apiService.getPhotoOfTheDay(apiKey)

    override suspend fun getPhotoOfADay(date: String): Response<AstronomicPhoto> =
        apiService.getPhotoOfADay(apiKey,date)

}