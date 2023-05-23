package app.reminders.presentation.features.todo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import app.common.base.BaseFragment
import app.common.extension.*
import app.common.utils.SwipeToDeleteCallback
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
    private lateinit var simpleItemTouchCallback: SwipeToDeleteCallback

    companion object {
        fun newInstance() = TodoListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orders = resources.getStringArray(R.array.OrderBy)
        registerListener()
        registerSwipeToDeleteCallback()
        todoListAdapter = TodoListAdapter(onClick = { item ->
            findNavController().navigate(
                TodoListFragmentDirections.actionTodoListFragmentToEditTodoFragment(
                    reminderItem = item
                )
            )
        }, onChecked = { item, isChecked ->
            viewModel.completeReminder(item.id, isChecked)
        })
        adapter = ArrayAdapter(
            mContext,
            R.layout.layout_spinner_item, orders
        )
        with(binding) {
            rvToDo.apply {
                switchableLayoutManager(mContext)
                adapter = todoListAdapter
            }
            val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(rvToDo)
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

    private fun registerSwipeToDeleteCallback() {
        simpleItemTouchCallback =
            object : SwipeToDeleteCallback(mContext) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    viewModel.deleteReminder(todoListAdapter.items[position].id)
                        .observe(viewLifecycleOwner) {
                            when (it) {
                                is TodoListViewState.Loading -> {
                                    showProgress()
                                }
                                is TodoListViewState.Error -> {
                                    hideProgress()
                                    snack(it.error.orUnknownError())
                                }
                                is TodoListViewState.Complete -> {
                                    hideProgress()
                                    todoListAdapter.removeAt(position)
                                }
                                is TodoListViewState.Content -> {}
                            }
                        }
                }
            }
    }

    private fun registerListener() {
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
            is TodoListViewState.Error -> {
                hideProgress()
                snack(viewState.error.orUnknownError())
            }
            is TodoListViewState.Complete -> {
                hideProgress()
                viewModel.filterList(isAscending = orderByAscending, isCompleted = isCompleted)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

}