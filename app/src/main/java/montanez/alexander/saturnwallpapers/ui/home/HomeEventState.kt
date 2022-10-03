package montanez.alexander.saturnwallpapers.ui.home

import montanez.alexander.saturnwallpapers.R

enum class HomeEventState(
    private val stringResource: Int
) {
    WALLPAPER_SAVED(R.string.message_save_wallpaper_success),
    WALLPAPER_SET(R.string.message_set_wallpaper_success),
    ERROR(R.string.message_error),
    IDLE(0);

    fun getStringResource() = stringResource
}