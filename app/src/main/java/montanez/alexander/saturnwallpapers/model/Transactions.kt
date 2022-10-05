package montanez.alexander.saturnwallpapers.model

enum class Transactions(
    private val code: Int,
    private val debuggable: Boolean
) {

    WORK_MANAGER_WALLPAPER_ATTEMPT(1,true),
    SYSTEM_WALLPAPER_CHANGED_MANUALLY(2,true),
    HOME_WALLPAPER_CHANGED_MANUALLY(3,true),
    LOCK_SCREEN_WALLPAPER_CHANGED_MANUALLY(4,true),
    WALLPAPER_DOWNLOADED(5,true),
    SERVICE_STATE_CHANGED(6,true),
    WALLPAPER_SCREEN_CHANGED(7,true),
    MEDIA_QUALITY_CHANGED(8,true),
    OTHER(9,true);


    companion object {
        private val enumValues = values()
        fun fromInt(code: Int) : Transactions {
            return if(code in 1..8)
                enumValues.firstOrNull { it.getCode() == code }!!
            else OTHER
        }
    }

    fun getCode() = code
    fun isDebuggable() = debuggable
}