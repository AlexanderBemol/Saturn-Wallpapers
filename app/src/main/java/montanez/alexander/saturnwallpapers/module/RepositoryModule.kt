package montanez.alexander.saturnwallpapers.module

import android.content.Context
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoDAO
import montanez.alexander.saturnwallpapers.api.IAstronomicPhotoHelper
import montanez.alexander.saturnwallpapers.api.LogDataDAO
import montanez.alexander.saturnwallpapers.api.PictureDownloader
import montanez.alexander.saturnwallpapers.repository.*
import montanez.alexander.saturnwallpapers.utils.LogManager
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun providePreferencesRepository(context: Context) :
        IPreferencesRepository = PreferencesRepository(context)
fun provideLogDataRepository(logDataDAO: LogDataDAO) :
        ILogDataRepository = LogDataRepository(logDataDAO)
fun provideMainRepository(apiHelper: IAstronomicPhotoHelper, wallpaperHelper: WallpaperHelper) :
        IMainRepository = MainRepository(apiHelper,wallpaperHelper)
fun provideFilesRepository(context: Context) :
        IFilesRepository = FilesRepository(context)
fun provideAstronomicRepository(
    apiHelper: IAstronomicPhotoHelper,
    dao: IAstronomicPhotoDAO,
    downloader: PictureDownloader,
    context: Context
) : IAstronomicPhotoRepository = AstronomicPhotoRepository(apiHelper,dao,downloader,context)

val reposModule = module {
    single { provideMainRepository(get(), get()) }
    single { providePreferencesRepository(androidContext()) }
    single { provideLogDataRepository(get()) }
    single { provideFilesRepository(androidContext()) }
    single { provideAstronomicRepository(get(),get(),PictureDownloader(),androidContext()) }
    single { LogManager(get()) }
}