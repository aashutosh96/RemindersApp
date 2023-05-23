package app.reminders.presentation.features.todo

import androidx.lifecycle.*
import app.reminders.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

const val ASC = "ASC"
const val DESC = "DESC"

class TodoListViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<TodoListViewState>()
    val viewState: LiveData<TodoListViewState> get() = _viewState

    fun filterList(isCompleted: Boolean, isAscending: Boolean) {
        viewModelScope.launch {
            remindersRepository.filterReminders(isAscending = isAscending, isComplete = isCompleted)
                .onStart { _viewState.postValue(TodoListViewState.Loading) }.collectLatest {
                    _viewState.postValue(TodoListViewState.Content(it))
                }
        }
    }

    fun completeReminder(id: String, isChecked: Boolean) {
        viewModelScope.launch {
            remindersRepository.toggleReminderComplete(isChecked, id)
                .onStart { _viewState.postValue(TodoListViewState.Loading) }.catch { e ->
                    _viewState.postValue(TodoListViewState.Error(e.localizedMessage))
                }
                .collect {
                    _viewState.postValue(TodoListViewState.Complete)
                }
        }
    }

    fun deleteReminder(id: String) = liveData {
        remindersRepository.deleteReminder(id)
            .onStart { emit(TodoListViewState.Loading) }.catch { e ->
                emit(TodoListViewState.Error(e.localizedMessage))
            }
            .collect {
                emit(TodoListViewState.Complete)
            }
    }
}