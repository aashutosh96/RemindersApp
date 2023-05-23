package app.reminders.presentation.features.todo

import app.reminders.domain.mapper.ReminderDomain

sealed class TodoListViewState {
    object Loading : TodoListViewState()
    object Complete : TodoListViewState()
    data class Content(val todoList: List<ReminderDomain>): TodoListViewState()
    data class Error(val error: String?): TodoListViewState()
}