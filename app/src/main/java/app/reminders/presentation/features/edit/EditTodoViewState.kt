package app.reminders.presentation.features.edit

sealed class EditTodoViewState {
    object Loading : EditTodoViewState()
    data class Error(val error: String?) : EditTodoViewState()
    object Complete : EditTodoViewState()
}