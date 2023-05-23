package app.reminders.domain.repository

import app.common.extension.getCurrentDateTimeUTC
import app.common.extension.getMilliSecondsFromDateTime
import app.reminders.data.source.LocalDataSource
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.mapper.mapDomainAsEntity
import app.reminders.domain.mapper.mapEntityAsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class RemindersRepositoryImpl(private val localDataSource: LocalDataSource) : RemindersRepository {
    override suspend fun getAllReminders(isAscending: Boolean): Flow<List<ReminderDomain>> {
        return localDataSource.getAllReminders(isAscending).map {
            mapEntityAsDomain(it)
        }
    }

    override suspend fun filterReminders(
        isAscending: Boolean,
        isComplete: Boolean
    ): Flow<List<ReminderDomain>> {
        return localDataSource.filterReminders(isAscending, isComplete).map {
            mapEntityAsDomain(it)
        }
    }

    override suspend fun addReminder(reminder: ReminderDomain) {
        val date = getCurrentDateTimeUTC(Date())
        return localDataSource.addReminder(
            mapDomainAsEntity(
                id = reminder.id,
                title = reminder.title,
                reminder = getMilliSecondsFromDateTime(reminder.dueDate),
                createdDate = getMilliSecondsFromDateTime(date),
                updatedDate = getMilliSecondsFromDateTime(date),
                isComplete = false,
            )
        )
    }

    override suspend fun editReminder(id: String, title: String, dueDate: String) {
        return localDataSource.editReminder(id, title, getMilliSecondsFromDateTime(dueDate))
    }

    override suspend fun deleteReminder(id: String) {
        return localDataSource.deleteReminder(id)
    }

    override suspend fun toggleReminderComplete(isComplete: Boolean, id: String) {
        return localDataSource.toggleReminderComplete(isComplete, id)
    }

}