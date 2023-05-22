package app.reminders.presentation.features.create

import androidx.lifecycle.*
import app.common.extension.FIRST_NAME_REGEX
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.repository.RemindersRepository
import kotlinx.coroutines.launch

const val TITLE = "Title"
const val DUE_DATE = "Due Date"

class CreateTodoViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<CreateTodoViewState>()
    val viewState: LiveData<CreateTodoViewState>
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

    fun createReminder(reminderDomain: ReminderDomain) {
        viewModelScope.launch {
            _viewState.postValue(CreateTodoViewState.Loading)
            remindersRepository.addReminder(reminderDomain)
            _viewState.postValue(CreateTodoViewState.Complete)
        }
    }
}