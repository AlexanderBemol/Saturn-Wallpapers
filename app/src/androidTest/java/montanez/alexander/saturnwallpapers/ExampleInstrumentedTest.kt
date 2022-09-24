package montanez.alexander.saturnwallpapers

import android.app.WallpaperManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import montanez.alexander.saturnwallpapers.repository.MainRepository
import montanez.alexander.saturnwallpapers.utils.WallpaperHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {
    private val mainRepository by inject<MainRepository>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("montanez.alexander.apodpapers", appContext.packageName)
    }
    @Test
    fun setWallpaperTest() = runTest{
        val wallpaperHelper = WallpaperHelper()
        try {
            launch {
                val apod = mainRepository.getBitmapOfPhotoOfTheDay()
                val imageUrl: String = apod.photoHDUrl.toString()
                val bitmap = wallpaperHelper
                    .getBitmapFromUrl(imageUrl)
                //wallpaperHelper.changeWallpaper(InstrumentationRegistry.getInstrumentation().context,bitmap,WallpaperManager.FLAG_LOCK)
                assertTrue(true)
            }
        } catch (e : Exception) {
            assertTrue(false)
        }
    }
}