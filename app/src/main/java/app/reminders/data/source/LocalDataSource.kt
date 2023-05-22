package app.reminders.data.source

import app.reminders.data.database.AppDao
import app.reminders.data.database.entities.ReminderEntity
import app.reminders.domain.mapper.ReminderDomain
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getAllReminders(): Flow<List<ReminderEntity>>
    suspend fun getReminderById(id: String): Flow<ReminderEntity>
    suspend fun addReminder(entity: ReminderEntity)
    suspend fun editReminder(id: String, title: String, dueDate: String)
    suspend fun deleteReminder(id: String)
    suspend fun toggleReminderComplete(isComplete: Boolean, id: String)
}