package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail

data class SignInMailUiState(
    var email: String = "", var password: String = "", val isLoginEnable : Boolean = false, val isLoading : Boolean = false
)

