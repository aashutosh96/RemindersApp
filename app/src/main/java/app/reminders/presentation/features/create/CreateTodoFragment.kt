package app.reminders.presentation.features.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import app.common.base.BaseFragment
import app.common.extension.*
import app.common.utils.randomUUID
import app.reminders.domain.mapper.ReminderDomain
import com.aashutosh.reminders.databinding.FragmentCreateTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateTodoFragment : BaseFragment<FragmentCreateTodoBinding>(), View.OnClickListener {
    private val viewModel: CreateTodoViewModel by viewModel()
    private var selectedDate = ""

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            listOf(reminderForm.etDueDate.binding.etAnswer, btnCreate).forEach {
                it.setOnSingleClickListener(this@CreateTodoFragment)
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            when(it) {
                is CreateTodoViewState.Loading -> {
                    showProgress()
                }
                is CreateTodoViewState.Complete -> {
                    hideProgress()
                    clearTextFields()
                }
                is CreateTodoViewState.Error -> {
                    hideProgress()
                    snack(it.error.orUnknownError())
                }
            }
        }
    }

    private fun clearTextFields() {
        with(binding) {
            reminderForm.etTitle.value = ""
            reminderForm.etDueDate.value = ""
            selectedDate = ""
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
                btnCreate -> {
                    val title = reminderForm.etTitle.value
                    val dueDate = reminderForm.etDueDate.value
                    viewModel.checkFormValidity(
                        title, dueDate
                    ).observe(viewLifecycleOwner) {
                        if (it.isValid) {
                            viewModel.createReminder(ReminderDomain(randomUUID(), title, selectedDate, false))
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
                else -> {}
            }
        }
    }

}