package montanez.alexander.saturnwallpapers.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import montanez.alexander.saturnwallpapers.BuildConfig
import montanez.alexander.saturnwallpapers.api.*
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.repository.*
import montanez.alexander.saturnwallpapers.ui.home.HomeViewModel
import montanez.alexander.saturnwallpapers.ui.onboarding.OnBoardingViewModel
import montanez.alexander.saturnwallpapers.ui.settings.SettingsViewModel
import montanez.alexander.saturnwallpapers.ui.splash.SplashViewModel
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun getApiService(retrofit: Retrofit): IAstronomicPhotosService = retrofit.create(IAstronomicPhotosService::class.java)

fun getAstronomicPhotoImpl(astronomicPhotosService: IAstronomicPhotosService): IAstronomicPhotoHelper = AstronomicPhotoImpl(astronomicPhotosService, Constants.APOD_KEY)

private val Context.preferencesDataStore by preferencesDataStore(Constants.DATA_STORE_NAME)

fun getDataStore(appContext: Context) : DataStore<Preferences>{
    return appContext.preferencesDataStore
}

fun getWallpaperHelper() = WallpaperHelper()

fun getRoomDatabase(appContext: Context) =
    Room.databaseBuilder(appContext,SaturnDatabase::class.java,Constants.DATABASE_NAME).build()

val appModule = module {
    single { getOkHttpClient() }
    single { getRetrofit(get()) }
    single { getApiService(get()) }
    single { getAstronomicPhotoImpl(get()) }
    single { getDataStore(androidContext()) }
    single { getRoomDatabase(androidContext()) }
    factory { getWallpaperHelper() }
}

fun provideLogDataDAO(db: SaturnDatabase) = db.logDataDao()

val daoModule = module {
    single { provideLogDataDAO(get()) }
}

fun providePreferencesRepository(context: Context) : IPreferencesRepository = PreferencesRepository(context)
fun provideLogDataRepository(logDataDAO: LogDataDAO) : ILogDataRepository = LogDataRepository(logDataDAO)
fun provideMainRepository(apiHelper: IAstronomicPhotoHelper, wallpaperHelper: WallpaperHelper) : IMainRepository =
    MainRepository(apiHelper,wallpaperHelper)
fun provideFilesRepository(context: Context) : IFilesRepository = FilesRepository(context)

val reposModule = module {
    single { provideMainRepository(get(), get()) }
    single { providePreferencesRepository(androidContext()) }
    single { provideLogDataRepository(get()) }
    single { provideFilesRepository(androidContext()) }
}

val viewModelsModule = module {
    viewModel { HomeViewModel(get(),get(), get(), get(), get(), androidApplication()) }
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
