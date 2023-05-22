package app.reminders.presentation.features.create

data class FormValidatorModel(
    val title: String,
    val message: String,
    val isValid: Boolean
)