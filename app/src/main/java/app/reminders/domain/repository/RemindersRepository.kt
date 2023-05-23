package app.reminders.domain.repository

import app.reminders.domain.mapper.ReminderDomain
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    suspend fun filterReminders(
        isAscending: Boolean,
        isComplete: Boolean
    ): Flow<List<ReminderDomain>>
    suspend fun addReminder(reminder: ReminderDomain): Flow<Unit>
    suspend fun editReminder(id: String, title: String, dueDate: String): Flow<Unit>
    suspend fun deleteReminder(id: String): Flow<Unit>
    suspend fun toggleReminderComplete(isComplete: Boolean, id: String): Flow<Unit>
}