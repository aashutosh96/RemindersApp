package app.reminders.presentation.features.create

sealed class CreateTodoViewState {
    object Loading : CreateTodoViewState()
    object Complete : CreateTodoViewState()
}