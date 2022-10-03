package montanez.alexander.saturnwallpapers.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import montanez.alexander.saturnwallpapers.model.AstronomicPhoto

@Dao
interface IAstronomicPhotoDAO {
    @Query("SELECT * FROM AstronomicPhoto")
    fun getAll() : List<AstronomicPhoto>

    @Insert
    fun insertOne(astronomicPhoto: AstronomicPhoto)

    @Delete
    fun delete(astronomicPhoto: AstronomicPhoto)

}