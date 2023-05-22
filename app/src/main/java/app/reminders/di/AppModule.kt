package app.reminders.di

import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import app.reminders.data.source.LocalDataSource
import app.reminders.data.source.LocalDataSourceImpl
import app.reminders.domain.repository.RemindersRepository
import app.reminders.domain.repository.RemindersRepositoryImpl
import app.reminders.presentation.features.create.CreateTodoViewModel
import app.reminders.presentation.features.edit.EditTodoViewModel
import app.reminders.presentation.features.todo.TodoListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Main + Job()) }
    single { Dispatchers.Default }
    single { provideResources(get()) }
    single { provideAssetManager(get()) }
    single { provideContentResolver(get()) }
}

val dataSourceModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
}

val repositoryModule = module {
    single<RemindersRepository> { RemindersRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { CreateTodoViewModel(get()) }
    viewModel { EditTodoViewModel(get()) }
    viewModel { TodoListViewModel(get()) }
}

fun provideResources(context: Context): Resources = context.resources

fun provideAssetManager(resources: Resources): AssetManager = resources.assets

fun provideContentResolver(context: Context): ContentResolver = context.contentResolver