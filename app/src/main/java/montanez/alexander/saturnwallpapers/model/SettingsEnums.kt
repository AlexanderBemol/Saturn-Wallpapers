package montanez.alexander.saturnwallpapers.model

enum class QualityOfImages(
    private val code: Int
){
    NORMAL_QUALITY(0),
    HIGH_QUALITY(1);
    companion object {
        fun fromInt(code: Int) : QualityOfImages {
            return if(code == 1) HIGH_QUALITY
            else NORMAL_QUALITY
        }
    }
    fun getCode(): Int = code
}

enum class ScreenOfWallpaper(
    private val code: Int
){
    BOTH_SCREENS(0),
    LOCK_SCREEN(1),
    HOME_SCREEN(2);

    companion object {
        fun fromInt(code: Int): ScreenOfWallpaper {
            return when (code) {
                2 -> HOME_SCREEN
                1 -> LOCK_SCREEN
                else -> BOTH_SCREENS
            }
        }
    }
    fun getCode(): Int = code
}