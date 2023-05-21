package app.reminders.domain.repository

import app.common.extension.currentDate
import app.reminders.data.database.entities.ReminderEntity
import app.reminders.data.source.LocalDataSource
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.mapper.mapDomainAsEntity
import app.reminders.domain.mapper.mapEntityAsDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RemindersRepositoryImpl(private val localDataSource: LocalDataSource) : RemindersRepository {
    override suspend fun getAllReminders(): Flow<List<ReminderDomain>> {
        val mapper: Flow<List<ReminderDomain>> = localDataSource.getAllReminders().map {
            mapEntityAsDomain(it)
        }
        return mapper
    }

    override suspend fun addReminder(reminder: ReminderDomain) {
        val mapper: ReminderEntity =
            mapDomainAsEntity(
                id = reminder.id,
                title = reminder.title,
                reminder = reminder.reminder,
                createdDate = currentDate(),
                updatedDate = currentDate()
            )
        return localDataSource.addReminder(mapper)
    }

    override suspend fun editReminder(id: String, title: String, dueDate: String) {
        return localDataSource.editReminder(id, title, dueDate)
    }

    override suspend fun deleteReminder(id: String) {
        return localDataSource.deleteReminder(id)
    }

}