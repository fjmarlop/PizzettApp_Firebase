package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount

data class CreateAccountUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    var isFormValid: Boolean = false
)
