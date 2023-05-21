package app.reminders.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.reminders.data.database.entities.ReminderEntity

@Database(
    entities = [
        ReminderEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}