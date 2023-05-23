package app.reminders.data.source

import app.reminders.data.database.AppDao
import app.reminders.data.database.entities.ReminderEntity
import app.reminders.domain.mapper.ReminderDomain
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun filterReminders(isAscending: Boolean, isComplete: Boolean): Flow<List<ReminderEntity>>
    suspend fun addReminder(entity: ReminderEntity): Flow<Unit>
    suspend fun editReminder(id: String, title: String, dueDate: Long): Flow<Unit>
    suspend fun deleteReminder(id: String): Flow<Unit>
    suspend fun toggleReminderComplete(isComplete: Boolean, id: String): Flow<Unit>
}