package app.reminders.presentation.features.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import app.common.base.BaseFragment
import app.common.extension.*
import app.reminders.domain.mapper.ReminderDomain
import app.reminders.presentation.features.create.DUE_DATE
import app.reminders.presentation.features.create.TITLE
import com.aashutosh.reminders.databinding.FragmentEditTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class EditTodoFragment : BaseFragment<FragmentEditTodoBinding>(), View.OnClickListener {
    private lateinit var args: EditTodoFragmentArgs
    private var selectedDate = ""
    private val viewModel: EditTodoViewModel by viewModel()

    companion object {
        fun newInstance() = EditTodoFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            args = EditTodoFragmentArgs.fromBundle(it)
            selectedDate = args.reminderItem.dueDate
            loggerE(selectedDate)
        }
        initUi()
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is EditTodoViewState.Loading -> {
                    showProgress()
                }
                is EditTodoViewState.Complete -> {
                    hideProgress()
                    findNavController().popBackStack()
                }
                is EditTodoViewState.Error -> {
                    hideProgress()
                    snack(it.error.orUnknownError())
                }
            }
        }
    }

    private fun initUi() {
        with(binding) {
            reminderForm.apply {
                etTitle.value = args.reminderItem.title
                etDueDate.value = parseServerDate(args.reminderItem.dueDate)
            }
            listOf(reminderForm.etDueDate.binding.etAnswer, btnEdit).forEach {
                it.setOnSingleClickListener(this@EditTodoFragment)
            }
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v) {
                reminderForm.etDueDate.binding.etAnswer -> {
                    try {
                        val cal = Calendar.getInstance()
                        DatePickerDialog(
                            mContext,
                            { view, year, monthOfYear, dayOfMonth ->
                                cal.set(Calendar.YEAR, year)
                                cal.set(Calendar.MONTH, monthOfYear)
                                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                TimePickerDialog(
                                    mContext,
                                    { view, hourOfDay, minute ->
                                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                                        cal.set(Calendar.MINUTE, minute)
                                        selectedDate = SimpleDateFormat(
                                            SERVER_TIME_DATE_FORMAT_WITH_TIME, Locale.ENGLISH
                                        ).format(cal.time).orEmpty()
                                        reminderForm.etDueDate.value = SimpleDateFormat(
                                            CUSTOM_DATE_TIME_FORMAT, Locale.ENGLISH
                                        ).format(cal.time).orEmpty()
                                    },
                                    cal.get(Calendar.HOUR_OF_DAY),
                                    cal.get(Calendar.MINUTE),
                                    true
                                ).show()
                            },
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        ).apply {
                            this.datePicker.minDate = System.currentTimeMillis()
                        }.show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                btnEdit -> {
                    val title = reminderForm.etTitle.value
                    val dueDate = reminderForm.etDueDate.value
                    viewModel.checkFormValidity(
                        title, dueDate
                    ).observe(viewLifecycleOwner) {
                        if (it.isValid) {
                            viewModel.editReminder(
                                ReminderDomain(
                                    args.reminderItem.id,
                                    title,
                                    selectedDate,
                                    args.reminderItem.isComplete
                                )
                            )
                        } else {
                            when (it.title) {
                                TITLE -> {
                                    reminderForm.etTitle.error(it.message)
                                }
                                DUE_DATE -> {
                                    reminderForm.etDueDate.error(it.message)
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }

}