package montanez.alexander.saturnwallpapers.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import montanez.alexander.saturnwallpapers.BuildConfig
import montanez.alexander.saturnwallpapers.api.AstronomicPhotoImpl
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoHelper
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotosService
import montanez.alexander.saturnwallpapers.api.SaturnDatabase
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getOkHttpClient() =
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .build()
    } else OkHttpClient
        .Builder()
        .build()

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getApiService(retrofit: Retrofit): IAstronomicPhotosService = retrofit.create(
    IAstronomicPhotosService::class.java)

fun getAstronomicPhotoImpl(astronomicPhotosService: IAstronomicPhotosService):
        IAstronomicPhotoHelper = AstronomicPhotoImpl(astronomicPhotosService, Constants.APOD_KEY)

private val Context.preferencesDataStore
    by preferencesDataStore(Constants.DATA_STORE_NAME)

fun getDataStore(appContext: Context) :
        DataStore<Preferences> = appContext.preferencesDataStore


fun getWallpaperHelper() = WallpaperHelper()

fun getRoomDatabase(appContext: Context) =
    Room.databaseBuilder(appContext, SaturnDatabase::class.java, Constants.DATABASE_NAME).build()

val sourceModule = module {
    single { getOkHttpClient() }
    single { getRetrofit(get()) }
    single { getApiService(get()) }
    single { getAstronomicPhotoImpl(get()) }
    single { getDataStore(androidContext()) }
    single { getRoomDatabase(androidContext()) }
    factory { getWallpaperHelper() }
}

fun provideLogDataDAO(db: SaturnDatabase) = db.logDataDao()
fun provideAstronomicPhotoDAO(db: SaturnDatabase) = db.astronomicPhotoDao()

val daoModule = module {
    single { provideLogDataDAO(get()) }
    single { provideAstronomicPhotoDAO(get()) }
}