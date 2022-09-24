package montanez.alexander.saturnwallpapers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class LogData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val transaction: Transactions,
    val result: Int,
    val error: String,
    val dateTime: Date
)