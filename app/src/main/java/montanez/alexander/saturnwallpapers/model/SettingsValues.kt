package montanez.alexander.saturnwallpapers.model

data class SettingsValues(
    val isServiceEnabled: Boolean = false,
    val qualityOfImages: QualityOfImages = QualityOfImages.NORMAL_QUALITY,
    val screenOfWallpaper: ScreenOfWallpaper = ScreenOfWallpaper.BOTH_SCREENS
)
