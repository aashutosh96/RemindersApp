package app.reminders.presentation.features.create

sealed class CreateTodoViewState {
    object Loading : CreateTodoViewState()
    data class Error(val error: String?) : CreateTodoViewState()
    object Complete : CreateTodoViewState()
}