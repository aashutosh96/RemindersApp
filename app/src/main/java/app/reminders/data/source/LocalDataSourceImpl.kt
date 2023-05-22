package app.reminders.data.source

import app.common.extension.getCurrentDateTimeUTC
import app.reminders.data.database.AppDao
import app.reminders.data.database.entities.ReminderEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

class LocalDataSourceImpl(
    private val appDao: AppDao, private val defaultDispatcher: CoroutineDispatcher,
) : LocalDataSource {
    override suspend fun getAllReminders(): Flow<List<ReminderEntity>> {
        return appDao.getAllReminders().flowOn(defaultDispatcher)
    }

    override suspend fun getReminderById(id: String): Flow<ReminderEntity> {
        return appDao.getReminder(id).flowOn(defaultDispatcher)
    }

    override suspend fun addReminder(entity: ReminderEntity) {
        appDao.addReminder(entity)
    }

    override suspend fun editReminder(id: String, title: String, dueDate: String) {
        appDao.updateReminder(queryTitle = title, queryDueDate = dueDate, queryId = id)
    }

    override suspend fun deleteReminder(id: String) {
        appDao.deleteReminder(queryId = id)
    }

    override suspend fun toggleReminderComplete(isComplete: Boolean, id: String) {
        val updatedDate = getCurrentDateTimeUTC(Date())
        appDao.setComplete(queryIsComplete = isComplete, queryId = id, queryDate = updatedDate)
    }

}