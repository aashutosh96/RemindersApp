package app.reminders.presentation.features.todo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import app.common.base.BaseFragment
import app.common.extension.gone
import app.common.extension.logger
import app.common.extension.switchableLayoutManager
import app.common.extension.visible
import com.aashutosh.reminders.R
import com.aashutosh.reminders.databinding.FragmentTodoListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TodoListFragment : BaseFragment<FragmentTodoListBinding>() {
    private val viewModel: TodoListViewModel by viewModel()
    private lateinit var todoListAdapter: TodoListAdapter
    private var orderByAscending = true
    private var isCompleted = false
    private var orders: Array<String> = arrayOf()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var itemSelectedListener: AdapterView.OnItemSelectedListener

    companion object {
        fun newInstance() = TodoListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orders = resources.getStringArray(R.array.OrderBy)
        itemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                orderByAscending = orders[position] == ASC
                viewModel.filterList(isAscending = orderByAscending, isCompleted = isCompleted)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoListAdapter = TodoListAdapter {

        }
        adapter = ArrayAdapter(
            mContext,
            android.R.layout.simple_spinner_item, orders
        )
        with(binding) {
            rvToDo.apply {
                switchableLayoutManager(mContext)
                adapter = todoListAdapter
            }
            spinnerOrder.adapter = adapter
            spinnerOrder.onItemSelectedListener = itemSelectedListener
            groupFilter.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    rbCompleted.id -> {
                        isCompleted = true
                        viewModel.filterList(
                            isAscending = orderByAscending,
                            isCompleted = isCompleted
                        )
                    }
                    rbDue.id -> {
                        isCompleted = false
                        viewModel.filterList(
                            isAscending = orderByAscending,
                            isCompleted = isCompleted
                        )
                    }
                }
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

    override fun onResume() {
        super.onResume()
    }

}