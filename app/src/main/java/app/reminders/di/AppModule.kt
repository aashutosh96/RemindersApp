package app.reminders.di

import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

val appModule = module {
    single { CoroutineScope(Dispatchers.Main + Job()) }
    single { Dispatchers.Default }
    single { provideResources(get()) }
    single { provideAssetManager(get()) }
    single { provideContentResolver(get()) }
}

val dataSourceModule = module {

}

val repositoryModule = module {
}

val viewModelModule = module {
}

fun provideResources(context: Context): Resources = context.resources

fun provideAssetManager(resources: Resources): AssetManager = resources.assets

fun provideContentResolver(context: Context): ContentResolver = context.contentResolver