package app.reminders.domain.mapper

import app.reminders.data.database.entities.ReminderEntity

class ReminderDomain(
    val id: String,
    val title: String,
    val reminder: String
)

fun mapEntityAsDomain(list: List<ReminderEntity>): List<ReminderDomain> {
    return list.map { entity ->
        ReminderDomain(
            id = entity.id,
            title = entity.title,
            reminder = entity.dueDate
        )
    }
}

fun mapDomainAsEntity(
    id: String,
    title: String,
    reminder: String,
    createdDate: String,
    updatedDate: String
): ReminderEntity {
    return ReminderEntity(
        id = id,
        title = title,
        createdAt = createdDate,
        updatedAt = updatedDate,
        dueDate = reminder,
    )
}