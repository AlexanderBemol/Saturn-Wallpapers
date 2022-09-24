package montanez.alexander.saturnwallpapers.api

import montanez.alexander.saturnwallpapers.model.AstronomicPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IAstronomicPhotosService {
    @GET("apod")
    suspend fun getPhotoOfTheDay(@Query("api_key") apiKey: String) : Response<AstronomicPhoto>

    @GET("apod")
    suspend fun getPhotoOfADay(@Query("api_key") apiKey: String, @Query("date") requestedDate: String) : Response<AstronomicPhoto>
}