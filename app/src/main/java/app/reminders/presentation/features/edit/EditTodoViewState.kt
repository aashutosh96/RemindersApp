package app.reminders.presentation.features.edit

sealed class EditTodoViewState {
    object Loading : EditTodoViewState()
    object Complete : EditTodoViewState()
}