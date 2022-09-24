package montanez.alexander.saturnwallpapers.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.squareup.picasso.Picasso
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper

class WallpaperHelper {
    fun changeWallpaper(context: Context, newWallpaper: Bitmap, screenOfWallpaper: ScreenOfWallpaper) {
        val wallpaperManager = WallpaperManager.getInstance(context)
        when(screenOfWallpaper){
            ScreenOfWallpaper.LOCK_SCREEN -> wallpaperManager.setBitmap(newWallpaper,null,true, WallpaperManager.FLAG_LOCK)
            ScreenOfWallpaper.HOME_SCREEN -> wallpaperManager.setBitmap(newWallpaper,null,true, WallpaperManager.FLAG_SYSTEM)
            else -> {
                wallpaperManager.setBitmap(newWallpaper,null,true, WallpaperManager.FLAG_SYSTEM)
                wallpaperManager.setBitmap(newWallpaper,null,true, WallpaperManager.FLAG_LOCK)
            }
        }

    }

    fun getBitmapFromUrl(url: String): Bitmap = Picasso.get().load(url).get()


}