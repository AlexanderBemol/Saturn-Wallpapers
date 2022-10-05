package montanez.alexander.saturnwallpapers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import montanez.alexander.saturnwallpapers.model.Constants
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}