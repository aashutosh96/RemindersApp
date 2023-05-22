package app.reminders.presentation.features.todo

import app.reminders.domain.mapper.ReminderDomain

sealed class TodoListViewState {
    object Loading : TodoListViewState()
    data class Content(val todoList: List<ReminderDomain>): TodoListViewState()
}