package montanez.alexander.saturnwallpapers.repository

import android.graphics.Bitmap

interface IFilesRepository {
    fun savePhotoToStorage(bitmap: Bitmap, filename: String)
}