package app.reminders.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val REMINDER_TABLE = "REMINDER_TABLE"

@Entity(tableName = REMINDER_TABLE)
class ReminderEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
)