package montanez.alexander.saturnwallpapers

import montanez.alexander.saturnwallpapers.model.QualityOfImages
import org.junit.Test

class HelpersTests {
    @Test
    fun qualityEnumsTests(){
        val codeNormal = 0
        val codeHigh = 1

        val testNormal = QualityOfImages.fromInt(codeNormal) == QualityOfImages.NORMAL_QUALITY
        val testHigh = QualityOfImages.fromInt(codeHigh) == QualityOfImages.HIGH_QUALITY

        assert(testNormal && testHigh)
    }
}