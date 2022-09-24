package montanez.alexander.saturnwallpapers.model

enum class Transactions(
    private val code: Int
) {

    WORK_MANAGER_WALLPAPER_ATTEMPT(1),
    WALLPAPER_CHANGED_MANUALLY(2);

    companion object {
        fun fromInt(code: Int) : Transactions {
            return if (code == 1 ) WORK_MANAGER_WALLPAPER_ATTEMPT
            else WALLPAPER_CHANGED_MANUALLY
        }
    }

    fun getCode(): Int = code
}