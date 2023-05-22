package app.reminders.presentation

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.common.base.BaseActivity
import com.aashutosh.reminders.R
import com.aashutosh.reminders.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = binding.navView

        val navController = this.findNavController(R.id.nav_host_fragment_activity_reminders)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.todoListFragment, R.id.createTodoFragment, R.id.preferencesFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}