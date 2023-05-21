package app.reminders.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import app.reminders.data.Prefs
import app.reminders.data.database.AppDatabase
import app.reminders.di.PersistenceDataSourceProperties.PREF_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {
    single { PREF_NAME.provideSharedPreference(get()) }
    single { providePrefsManager(get()) }
    single {
        Room
            .databaseBuilder(androidApplication(), AppDatabase::class.java, "Reminders Db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<AppDatabase>().appDao()
    }
}

object PersistenceDataSourceProperties {
    const val PREF_NAME = "Reminders.App.preference"
}

private fun String.provideSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences(this, Context.MODE_PRIVATE)
}

private fun providePrefsManager(sharedPreferences: SharedPreferences): Prefs {
    return Prefs(sharedPreferences)
}





