package montanez.alexander.saturnwallpapers.model

data class SettingsValues(
    val isServiceEnabled: Boolean = true,
    val qualityOfImages: QualityOfImages = QualityOfImages.NORMAL_QUALITY,
    val screenOfWallpaper: ScreenOfWallpaper = ScreenOfWallpaper.BOTH_SCREENS
)
