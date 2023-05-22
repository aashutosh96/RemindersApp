package app.reminders.presentation.features.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.domain.repository.RemindersRepository
import kotlinx.coroutines.launch

class EditTodoViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<EditTodoViewState>()
    val viewState: LiveData<EditTodoViewState>
        get() = _viewState

    fun editReminder(reminderDomain: ReminderDomain) {
        viewModelScope.launch {
            _viewState.postValue(EditTodoViewState.Loading)
            remindersRepository.editReminder(reminderDomain.id, reminderDomain.title, reminderDomain.dueDate)
            _viewState.postValue(EditTodoViewState.Complete)
        }
    }
}