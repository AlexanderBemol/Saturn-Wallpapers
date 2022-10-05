package montanez.alexander.saturnwallpapers.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class LogData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val transaction: Transactions,
    val dateTime: Date = Date(),
    val success: Boolean,
    val observations: String = "",
    val className: String,
    val oldValue: String = "",
    val newValue: String = ""
)