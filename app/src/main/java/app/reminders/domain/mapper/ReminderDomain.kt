package app.reminders.domain.mapper

import android.os.Parcelable
import app.common.extension.getDateFromMillis
import app.reminders.data.database.entities.ReminderEntity
import kotlinx.parcelize.Parcelize

@Parcelize
class ReminderDomain(
    val id: String,
    val title: String,
    val dueDate: String,
    val isComplete: Boolean
): Parcelable

fun mapEntityAsDomain(list: List<ReminderEntity>): List<ReminderDomain> {
    return list.map { entity ->
        ReminderDomain(
            id = entity.id,
            title = entity.title,
            dueDate = getDateFromMillis(entity.dueDate),
            isComplete = entity.isComplete
        )
    }
}

fun mapDomainAsEntity(
    id: String,
    title: String,
    reminder: Long,
    createdDate: Long,
    updatedDate: Long,
    isComplete: Boolean,
): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        createdAt = createdDate,
        updatedAt = updatedDate,
        dueDate = reminder,
        isComplete = isComplete
    )
}