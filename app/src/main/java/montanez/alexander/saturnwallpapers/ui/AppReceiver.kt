package montanez.alexander.saturnwallpapers.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AppReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.extras
    }

}