package app.reminders.presentation.features.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.common.base.BaseFragment
import com.aashutosh.reminders.R
import com.aashutosh.reminders.databinding.FragmentEditTodoBinding

class EditTodoFragment : BaseFragment<FragmentEditTodoBinding>() {

    companion object {
        fun newInstance() = EditTodoFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}