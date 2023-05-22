package app.reminders.domain.repository

import app.common.extension.getCurrentDateTimeUTC
import app.common.extension.logger
import app.reminders.data.source.LocalDataSource
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.mapper.mapDomainAsEntity
import app.reminders.domain.mapper.mapEntityAsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.util.*

class RemindersRepositoryImpl(private val localDataSource: LocalDataSource) : RemindersRepository {
    override suspend fun getAllReminders(): Flow<List<ReminderDomain>> {
        return localDataSource.getAllReminders().map {
            mapEntityAsDomain(it)
        }
    }

    override suspend fun addReminder(reminder: ReminderDomain) {
        val date = Date()
        return localDataSource.addReminder(
            mapDomainAsEntity(
                id = reminder.id,
                title = reminder.title,
                reminder = reminder.dueDate,
                createdDate = getCurrentDateTimeUTC(date),
                updatedDate = getCurrentDateTimeUTC(date),
                isComplete = false,
            )
        )
    }

    override suspend fun editReminder(id: String, title: String, dueDate: String) {
        return localDataSource.editReminder(id, title, dueDate)
    }

    override suspend fun deleteReminder(id: String) {
        return localDataSource.deleteReminder(id)
    }

    override suspend fun toggleReminderComplete(isComplete: Boolean, id: String) {
        return localDataSource.toggleReminderComplete(isComplete, id)
    }

}