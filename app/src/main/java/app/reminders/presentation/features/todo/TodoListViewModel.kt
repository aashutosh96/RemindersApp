package app.reminders.presentation.features.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.reminders.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

const val ASC = "ASC"
const val DESC = "DESC"

class TodoListViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<TodoListViewState>()
    val viewState: LiveData<TodoListViewState> get() = _viewState

    fun loadTodoList(isAscending: Boolean = true) {
        viewModelScope.launch {
            remindersRepository.getAllReminders(isAscending)
                .onStart { _viewState.postValue(TodoListViewState.Loading) }.collectLatest {
                    _viewState.postValue(TodoListViewState.Content(it))
                }
        }
    }

    fun filterList(isCompleted: Boolean, isAscending: Boolean) {
        viewModelScope.launch {
            remindersRepository.filterReminders(isAscending = isAscending, isComplete = isCompleted)
                .onStart { _viewState.postValue(TodoListViewState.Loading) }.collectLatest {
                    _viewState.postValue(TodoListViewState.Content(it))
                }
        }
    }
}