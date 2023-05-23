package app.reminders.domain.repository

import app.reminders.domain.mapper.ReminderDomain
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    suspend fun getAllReminders(isAscending: Boolean): Flow<List<ReminderDomain>>
    suspend fun filterReminders(
        isAscending: Boolean,
        isComplete: Boolean
    ): Flow<List<ReminderDomain>>

    suspend fun addReminder(reminder: ReminderDomain)
    suspend fun editReminder(id: String, title: String, dueDate: String)
    suspend fun deleteReminder(id: String)
    suspend fun toggleReminderComplete(isComplete: Boolean, id: String)
}