package app.reminders.presentation.features.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.common.base.BaseFragment
import com.aashutosh.reminders.R
import com.aashutosh.reminders.databinding.FragmentCreateTodoBinding

class CreateTodoFragment : BaseFragment<FragmentCreateTodoBinding>() {

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

}