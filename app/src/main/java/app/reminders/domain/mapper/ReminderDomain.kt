package app.reminders.domain.mapper

import app.reminders.data.database.entities.ReminderEntity

class ReminderDomain(
    val id: String,
    val title: String,
    val dueDate: String,
    val isComplete: Boolean
)

fun mapEntityAsDomain(list: List<ReminderEntity>): List<ReminderDomain> {
    return list.map { entity ->
        ReminderDomain(
            id = entity.id,
            title = entity.title,
            dueDate = entity.dueDate,
            isComplete = entity.isComplete
        )
    }
}

fun mapDomainAsEntity(
    id: String,
    title: String,
    reminder: String,
    createdDate: String,
    updatedDate: String,
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