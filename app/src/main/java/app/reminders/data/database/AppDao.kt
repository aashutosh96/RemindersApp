package app.reminders.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import app.reminders.data.database.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT * FROM reminder_table")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminder_table WHERE title = :query")
    suspend fun getReminder(query: String): ReminderEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(entity: ReminderEntity)

    @Query("UPDATE reminder_table SET title = :queryTitle, due_date = :queryDueDate WHERE id = :queryId")
    suspend fun updateReminder(queryTitle: String, queryDueDate: String, queryId: String)

    @Query("DELETE FROM reminder_table WHERE id = :queryId")
    suspend fun deleteReminder(queryId: String)
}