package app.reminders.presentation.features.todo

import android.os.Bundle
import android.view.View
import app.common.base.BaseFragment
import app.common.extension.gone
import app.common.extension.switchableLayoutManager
import app.common.extension.visible
import com.aashutosh.reminders.databinding.FragmentTodoListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : BaseFragment<FragmentTodoListBinding>() {
    private val viewModel: TodoListViewModel by viewModel()
    private lateinit var todoListAdapter: TodoListAdapter

    companion object {
        fun newInstance() = TodoListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTodoList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListAdapter = TodoListAdapter {

        }
        with(binding) {
            rvToDo.apply {
                switchableLayoutManager(mContext)
                adapter = todoListAdapter
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            viewState ?: return@observe
            updateUI(viewState)
        }
    }

    private fun updateUI(viewState: TodoListViewState) {
        when (viewState) {
            is TodoListViewState.Loading -> {
                showProgress()
            }
            is TodoListViewState.Content -> {
                hideProgress()
                todoListAdapter.items = viewState.todoList
                if (viewState.todoList.isEmpty()) binding.tvNoReminders.visible() else binding.tvNoReminders.gone()
            }
        }
    }

}