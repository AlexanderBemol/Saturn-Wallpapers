package montanez.alexander.saturnwallpapers.model

class Constants {
    companion object{

        //Make these const will make koin retrofit factory crash
        val BASE_URL = "https://api.nasa.gov/planetary/"
        val APOD_KEY = "GVWuXLsKY0JFFo1kvZ1YgeHyD9AONA1S2wcrwaSq"

        const val WORK_MANAGER_DAILY_ID = "SATURN_WALLPAPERS_DAILY_WORKER"
        const val WORK_MANAGER_DAILY_TAG = "SATURN_WALLPAPERS_DAILY_WORKER_TAG"

        const val APP_DEBUG_TAG = "SATURN_PAPERS_DEBUG"

        const val MEDIA_TYPE_IMAGE = "image"
        const val MEDIA_TYPE_VIDEO = "video"

        const val DATE_FORMAT = "yyyy-MM-dd"
        const val READABLE_DATE_FORMAT = "EEEE MMMM dd, yyyy"

        const val DEFAULT_APOD_DATE = "2022-03-17"
        const val DEFAULT_APOD_EXPLANATION = "A mere 11 million light-years away, Centaurus A is the closest active galaxy to planet Earth. Spanning over 60,000 light-years, the peculiar elliptical galaxy also known as NGC 5128, is featured in this sharp telescopic view. Centaurus A is apparently the result of a collision of two otherwise normal galaxies resulting in a fantastic jumble of star clusters and imposing dark dust lanes. Near the galaxy's center, leftover cosmic debris is steadily being consumed by a central black hole with a billion times the mass of the Sun. As in other active galaxies, that process likely generates the enormous radio, X-ray, and gamma-ray energy radiated by Centaurus A."
        const val DEFAULT_APOD_COPYRIGHT = "David Alemazkour"
        const val DEFAULT_APOD_HD_URL = "https://apod.nasa.gov/apod/image/2203/CentaurusA_DavidAlemazkour.jpg"
        const val DEFAULT_APOD_REGULAR_URL = "https://apod.nasa.gov/apod/image/2203/CentaurusA_DavidAlemazkour.jpg"
        const val DEFAULT_APOD_TITLE = "Centaurus A"

        const val DATA_STORE_NAME = "SATURNDATA"
        const val DATABASE_NAME = "SATURN_DB"

        const val KEY_ON_BOARDING_COMPLETED = "ON_BOARDING_COMPLETED"
        const val KEY_SETTINGS_QUALITY = "SETTINGS_QUALITY"
        const val KEY_SETTINGS_SCREEN = "SETTINGS_SCREEN"
        const val KEY_SETTINGS_SERVICE_ENABLED = "SETTINGS_SERVICE_ENABLED"

        const val PING_TIME : Int = 3000
        const val WORKER_PERIOD = 4L
    }

}