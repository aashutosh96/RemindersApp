package app.reminders.presentation.features.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.common.extension.logger
import app.reminders.domain.repository.RemindersRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TodoListViewModel(private val remindersRepository: RemindersRepository) : ViewModel() {
    private val _viewState = MutableLiveData<TodoListViewState>()
    val viewState: LiveData<TodoListViewState> get() = _viewState

    fun loadTodoList() {
        viewModelScope.launch {
            remindersRepository.getAllReminders()
                .onStart { _viewState.postValue(TodoListViewState.Loading) }.collectLatest {
                _viewState.postValue(TodoListViewState.Content(it))
            }
        }
    }
}