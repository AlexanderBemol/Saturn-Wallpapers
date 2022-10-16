package montanez.alexander.saturnwallpapers.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoDAO
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoHelper
import montanez.alexander.saturnwallpapers.api.PictureDownloader
import montanez.alexander.saturnwallpapers.model.*
import montanez.alexander.saturnwallpapers.utils.*
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*
import kotlin.NoSuchElementException

class AstronomicPhotoRepository(
    private val apiHelper: IAstronomicPhotoHelper,
    private val astronomicPhotoDAO: IAstronomicPhotoDAO,
    private val downloader: PictureDownloader,
    private val context: Context
) : IAstronomicPhotoRepository{

    override suspend fun getAstronomicPhoto(deviceDate : Date, qualityOfImages: QualityOfImages) : TaskResult<AstronomicPhoto>{
        return try{ //first try to get offline data
            val dataList = astronomicPhotoDAO.getAll()
            val dataForTheDay = dataList.firstOrNull{x ->
                x.deviceDate.isTheSameDayAs(deviceDate) && x.mediaQuality == qualityOfImages }

            if(dataForTheDay != null) {
                dataForTheDay.picture = readPhotoFromInternal(dataForTheDay)
                TaskResult.Success(dataForTheDay)
            } else { //if there´s none
                return if(NetworkConnection.isOnline()){ //if there´s internet available
                    val response = apiHelper.getPhotoOfADay(deviceDate.getParsedDate())
                    if (isAValidResponse(response)) { //if the response is valid
                        val responseBody = response.body()!!
                        responseBody.deviceDate = deviceDate
                        val photoResponse = downloader.getPictureFromUrl(
                            if(qualityOfImages == QualityOfImages.HIGH_QUALITY
                                && responseBody.photoHDUrl != null) responseBody.photoHDUrl.toString()
                            else responseBody.photoRegularUrl.toString()
                        )
                        when(photoResponse){
                            is TaskResult.Success -> {
                                responseBody.picture = photoResponse.data
                                savePhotoToInternal(responseBody)
                                responseBody.localFilename = responseBody.date.toTimestampFilename()
                                responseBody.mediaQuality = qualityOfImages
                                astronomicPhotoDAO.insertOne(responseBody)
                                TaskResult.Success(responseBody)
                            }
                            is TaskResult.Error -> {
                                TaskResult.Error(photoResponse.e)
                            }
                        }
                    }
                    else { //get default data, insert data for today with default data
                        val defaultData = dataList.firstOrNull{
                                x->x.date.isTheSameDayAs(Constants.DEFAULT_APOD_DATE.getDateFromString())
                                    && x.mediaQuality == qualityOfImages
                        }
                        if(defaultData != null){
                            astronomicPhotoDAO.insertOne(defaultData)
                            defaultData.picture = readPhotoFromInternal(defaultData)
                            defaultData.deviceDate = deviceDate
                            TaskResult.Success(defaultData)
                        } else { //dowload default
                            val response = apiHelper.getPhotoOfADay(Constants.DEFAULT_APOD_DATE)
                            val responseBody = response.body()!!
                            responseBody.deviceDate = deviceDate
                            val photoResponse = downloader.getPictureFromUrl(
                                if(qualityOfImages == QualityOfImages.HIGH_QUALITY
                                    && responseBody.photoHDUrl != null) responseBody.photoHDUrl.toString()
                                else responseBody.photoRegularUrl.toString()
                            )
                            when(photoResponse){
                                is TaskResult.Success -> {
                                    responseBody.picture = photoResponse.data
                                    savePhotoToInternal(responseBody)
                                    responseBody.localFilename = responseBody.date.toTimestampFilename()
                                    responseBody.mediaQuality = qualityOfImages
                                    astronomicPhotoDAO.insertOne(responseBody)
                                    TaskResult.Success(responseBody)
                                }
                                is TaskResult.Error -> {
                                    TaskResult.Error(photoResponse.e)
                                }
                            }
                        }
                    }
                } else TaskResult.Error(PhoneNetworkException())
            }
        } catch (e : NoSuchElementException){
            TaskResult.Error(NotDataForDayFoundException())
        } catch (e : MissingPhotoException){ //Corrupted Data, will delete record and try again
            astronomicPhotoDAO.delete(e.astronomicPhoto)
            getAstronomicPhoto(e.astronomicPhoto.deviceDate, e.astronomicPhoto.mediaQuality)
        } catch (e : Exception){
            TaskResult.Error(e)
        }
    }

    override suspend fun getPicture(pictureUrl: String) : TaskResult<Bitmap>{
        return if(NetworkConnection.isOnline()){
            try {
                downloader.getPictureFromUrl(pictureUrl)
            } catch (e : Exception){
                TaskResult.Error(e)
            }
        } else {
            TaskResult.Error(PhoneNetworkException())
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

    private fun savePhotoToInternal(astronomicPhoto: AstronomicPhoto){
        val file = File(context.filesDir,astronomicPhoto.deviceDate.toTimestampFilename())
        val outputStream = FileOutputStream(file) as OutputStream
        astronomicPhoto.picture?.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun readPhotoFromInternal(astronomicPhoto: AstronomicPhoto) : Bitmap{
        val file = File(context.filesDir,astronomicPhoto.deviceDate.toTimestampFilename())
        if(file.exists()) return BitmapFactory.decodeFile(file.path)
        else throw(MissingPhotoException(astronomicPhoto))
    }

}