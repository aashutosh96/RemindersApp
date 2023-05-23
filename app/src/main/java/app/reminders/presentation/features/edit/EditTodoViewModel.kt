package app.reminders.presentation.features.edit

import androidx.lifecycle.*
import app.common.extension.FIRST_NAME_REGEX
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.repository.RemindersRepository
import app.reminders.presentation.features.create.DUE_DATE
import app.reminders.presentation.features.create.FormValidatorModel
import app.reminders.presentation.features.create.TITLE
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class EditTodoViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<EditTodoViewState>()
    val viewState: LiveData<EditTodoViewState>
        get() = _viewState

    fun checkFormValidity(title: String, dueDate: String) = liveData {
        if (!title.matches(Regex(FIRST_NAME_REGEX))) {
            emit(FormValidatorModel(TITLE, "Title must be at least 3 chars", false))
        }
        if (dueDate.isEmpty()) {
            emit(FormValidatorModel(DUE_DATE, "Due Date cannot be empty", false))
        }
        if (title.matches(Regex(FIRST_NAME_REGEX)) && dueDate.isNotEmpty()) {
            emit(FormValidatorModel("", "", true))
        }
    }

    fun editReminder(reminderDomain: ReminderDomain) {
        viewModelScope.launch {
            remindersRepository.editReminder(
                reminderDomain.id,
                reminderDomain.title,
                reminderDomain.dueDate
            ).onStart { _viewState.postValue(EditTodoViewState.Loading) }.catch { e ->
                _viewState.postValue(EditTodoViewState.Error(e.localizedMessage))
            }.collect {
                _viewState.postValue(EditTodoViewState.Complete)
            }
        }
    }
}