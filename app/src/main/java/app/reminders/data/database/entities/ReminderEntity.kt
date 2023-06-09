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
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "due_date")
    val dueDate: Long,
    @ColumnInfo(name = "is_complete")
    val isComplete: Boolean
)

