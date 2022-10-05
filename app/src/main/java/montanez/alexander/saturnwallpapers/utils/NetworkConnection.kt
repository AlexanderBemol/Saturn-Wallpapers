package montanez.alexander.saturnwallpapers.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.model.Constants
import java.net.InetSocketAddress
import java.net.Socket

class NetworkConnection {
    companion object {
        suspend fun isOnline() : Boolean{
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            return try{
                withContext(Dispatchers.IO) {
                    sock.connect(socketAddress, Constants.PING_TIME)
                    sock.close()
                    delay(1000)
                    true
                }
            } catch ( e : Exception){
                false
            }
        }
    }
}