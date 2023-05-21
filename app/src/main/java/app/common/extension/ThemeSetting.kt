package app.common.extension

import androidx.appcompat.app.AppCompatDelegate

fun changeToDarkTheme(){
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
}

fun changeToLightTheme(){
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}