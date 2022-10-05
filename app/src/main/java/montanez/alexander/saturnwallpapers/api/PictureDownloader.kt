package montanez.alexander.saturnwallpapers.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import montanez.alexander.saturnwallpapers.model.TaskResult
import java.net.URL

class PictureDownloader {
    fun getPictureFromUrl(pictureUrl: String) : TaskResult<Bitmap> {
        return try{
            val url = URL(pictureUrl)
            TaskResult.Success(
                BitmapFactory.decodeStream(
                    url.openConnection().getInputStream()
                )
            )
        } catch (e : Exception){
            TaskResult.Error(e)
        }
    }
}