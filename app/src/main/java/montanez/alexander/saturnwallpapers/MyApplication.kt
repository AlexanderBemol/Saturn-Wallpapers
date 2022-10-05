package montanez.alexander.saturnwallpapers

import android.app.Application
import montanez.alexander.saturnwallpapers.module.sourceModule
import montanez.alexander.saturnwallpapers.module.daoModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import montanez.alexander.saturnwallpapers.module.reposModule
import montanez.alexander.saturnwallpapers.module.viewModelsModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                sourceModule,
                daoModule,
                reposModule,
                viewModelsModule
            )
        }
    }
}