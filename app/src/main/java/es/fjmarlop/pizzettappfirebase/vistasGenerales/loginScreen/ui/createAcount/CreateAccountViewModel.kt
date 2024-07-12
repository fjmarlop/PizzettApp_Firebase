package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val utils: Utils,
    private val auth: AuthService,
) : ViewModel() {


    private val _uiState = MutableStateFlow(CreateAccountUiState())
    val uiState: StateFlow<CreateAccountUiState> = _uiState

    fun setEmailText(it: String) {
        _uiState.value = _uiState.value.copy(email = it)
        _uiState.value = _uiState.value.copy(isEmailValid = utils.isValidEmail(it))
        enableCreateButton()
    }

    fun setPasswordText(it: String) {
        _uiState.value = _uiState.value.copy(password = it)
        _uiState.value = _uiState.value.copy(isPasswordValid = utils.isValidPassword(it))
        enableCreateButton()
    }

    fun setReplyPasswordText(it: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = it)
        _uiState.value = _uiState.value.copy(isConfirmPasswordValid = isPasswordsEqual())
        enableCreateButton()
    }



    private fun isPasswordsEqual(): Boolean {
        val pass: String = _uiState.value.password
        val replyPass: String = _uiState.value.confirmPassword
        return pass == replyPass
    }

    private fun enableCreateButton() {
        val email: Boolean = uiState.value.isEmailValid && uiState.value.email != ""
        val pass: Boolean = _uiState.value.isPasswordValid && _uiState.value.password != ""
        val replyPass: Boolean = _uiState.value.isConfirmPasswordValid
        uiState.value.isFormValid = email && pass && replyPass
    }

    fun resetUiState() = runCatching { _uiState.value = CreateAccountUiState() }

    fun createAccount(navigate: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = auth.createAccount(uiState.value.email, uiState.value.password)
            withContext(Dispatchers.Main) {
                if (result != null) {
                    utils.msgToastShort("Cuenta creada correctamente")
                    //browsers.navigateToLoginScreen(navHost)
                    navigate()
                } else {
                    utils.msgToastShort("La cuenta no ha sido creada")
                    resetUiState()
                }
            }
        }
    }
}
