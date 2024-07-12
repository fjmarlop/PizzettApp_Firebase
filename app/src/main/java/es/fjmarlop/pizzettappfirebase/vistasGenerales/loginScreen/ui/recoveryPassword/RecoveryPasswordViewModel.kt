package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.recoveryPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class RecoveryPasswordViewModel @Inject constructor(
    private val utils: Utils,
    private val auth: AuthService
) : ViewModel() {


    private val _uiState = MutableStateFlow(RecoveryPasswordUiState())
    val uiState: StateFlow<RecoveryPasswordUiState> = _uiState

    fun resetUiState() {
        _uiState.value = RecoveryPasswordUiState()
    }

    fun setTextEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
        _uiState.value = _uiState.value.copy(isRecoveryButtonEnable = utils.isValidEmail(email))
    }

    fun recoveryPassword(email: String, back: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = withContext(Dispatchers.IO) {
                try {
                    auth.recoveryPassword(email)
                } catch (e: Exception) {
                    false
                }
            }

            delay(2000)

            if (result) {
                utils.msgToastShort("Se ha enviado un correo para restablecer la contraseña")
                back()
            } else {
                utils.msgToastShort("No se ha podido enviar el correo para restablecer la contraseña")
                resetUiState()
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}

