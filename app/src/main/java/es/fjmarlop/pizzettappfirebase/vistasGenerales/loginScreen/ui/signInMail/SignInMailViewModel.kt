package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.data.SplashService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SignInMailViewModel @Inject constructor(
    private val auth: AuthService,
    private val utils: Utils,
    private val splashService: SplashService
) : ViewModel() {


    private val _uiState = MutableStateFlow(SignInMailUiState())
    val uiState: StateFlow<SignInMailUiState> = _uiState


    fun setEmailText(email: String, password: String) {
        _uiState.value = _uiState.value.copy(email = email)
        _uiState.value = _uiState.value.copy(password = password)
        _uiState.value = _uiState.value.copy(isLoginEnable = enableLoginButton())
    }

    fun setPasswordText(password: String, email: String) {
        _uiState.value = _uiState.value.copy(password = password)
        _uiState.value = _uiState.value.copy(email = email)
        _uiState.value = _uiState.value.copy(isLoginEnable = enableLoginButton())
    }

    fun resetUiState() = runCatching { _uiState.value = SignInMailUiState() }

    private fun enableLoginButton(): Boolean {
        return utils.isValidEmail(_uiState.value.email) && utils.isValidPassword(_uiState.value.password)
    }

    fun loginEmail(focusEmail: () -> Unit, navigateManagement: () -> Unit, navigateClient: () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {

                val result = withContext(Dispatchers.IO) {
                    auth.initSession(
                        _uiState.value.email,
                        _uiState.value.password
                    )
                }
                delay(2000)

                if (result != null) {
                    utils.msgToastShort("Sesión iniciada correctamente")

                    if(isEmpleado(_uiState.value.email)) {
                        //browsers.navigateToMainManagementScreen(navHost)
                        navigateManagement()
                    } else {
                        //browsers.navigateToMainClientScreen(navHost)
                        navigateClient()
                    }
                }

            } catch (e: FirebaseAuthInvalidCredentialsException) {
                delay(2000)
                utils.msgToastShort("Error al iniciar sesión, revise sus credenciales")
                resetUiState()
                focusEmail()
            }

            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    private suspend fun isEmpleado(email: String): Boolean {

        val isEmpleado = suspendCoroutine { continuation ->
            viewModelScope.launch(Dispatchers.IO) {
                val empl = splashService.findByEmailEmployee(email)
                continuation.resume(empl)
            }

        }
        return isEmpleado != null
    }
}