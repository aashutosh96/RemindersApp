package app.reminders.data.source

import app.common.extension.getDateInMillis
import app.reminders.data.database.AppDao
import app.reminders.data.database.entities.ReminderEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

class LocalDataSourceImpl(
    private val appDao: AppDao, private val defaultDispatcher: CoroutineDispatcher,
) : LocalDataSource {

    override suspend fun filterReminders(
        isAscending: Boolean,
        isComplete: Boolean
    ): Flow<List<ReminderEntity>> = flow {
        if (isAscending) {
            emit(appDao.filterRemindersASC(isComplete))
        } else {
            emit(appDao.filterRemindersDESC(isComplete))
        }
    }.flowOn(defaultDispatcher)


    override suspend fun addReminder(entity: ReminderEntity) = flow {
        appDao.addReminder(entity)
        emit(Unit)
    }.flowOn(defaultDispatcher)

    override suspend fun editReminder(id: String, title: String, dueDate: Long) = flow {
        val updatedDate = getDateInMillis(Date())
        appDao.updateReminder(
            queryTitle = title,
            queryDueDate = dueDate,
            queryDate = updatedDate,
            queryId = id
        )
        emit(Unit)
    }.flowOn(defaultDispatcher)

    override suspend fun deleteReminder(id: String) = flow {
        appDao.deleteReminder(queryId = id)
        emit(Unit)
    }.flowOn(defaultDispatcher)

    override suspend fun toggleReminderComplete(isComplete: Boolean, id: String) = flow {
        val updatedDate = getDateInMillis(Date())
        appDao.setComplete(queryIsComplete = isComplete, queryId = id, queryDate = updatedDate)
        emit(Unit)
    }.flowOn(defaultDispatcher)

}