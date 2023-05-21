package app.reminders.data.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refresh")
    val refresh: String,
)