package app.reminders.domain.repository

import app.reminders.data.database.entities.ReminderEntity
import app.reminders.domain.mapper.ReminderDomain
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    suspend fun getAllReminders(): Flow<List<ReminderDomain>>
    suspend fun addReminder(reminder: ReminderDomain)
    suspend fun editReminder(id: String, title: String, dueDate: String)
    suspend fun deleteReminder(id: String)
}