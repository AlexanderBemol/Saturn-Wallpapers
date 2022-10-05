package montanez.alexander.saturnwallpapers.module

import montanez.alexander.saturnwallpapers.ui.home.HomeViewModel
import montanez.alexander.saturnwallpapers.ui.onboarding.OnBoardingViewModel
import montanez.alexander.saturnwallpapers.ui.settings.SettingsViewModel
import montanez.alexander.saturnwallpapers.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { HomeViewModel(get(),get(), get(), get(),get(),androidApplication()) }
    viewModel { SplashViewModel(get()) }
    viewModel { OnBoardingViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
