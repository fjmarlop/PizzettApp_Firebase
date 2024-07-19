package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.mainLogin

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    fun inGoogleLoginSelected(
        googleLauncherLogin: (GoogleSignInClient) -> Unit
    ) {
        val gsc = authService.getGoogleClient()
        googleLauncherLogin(gsc)
    }

    fun loginWithGoogle(idToken: String, navigate: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { authService.loginWithGoogle(idToken) }
            if (result != null) {
                //browsers.navigateToMainClientScreen(navHost)
                navigate()
            }
        }
    }

    fun loginWithTwitter(activity: Activity, navigate: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { authService.loginWithTwitter(activity) }
            if (result != null) {
                navigate()
            }
        }
    }

    fun anonymousLogin(navigate: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { authService.loginAnonymously() }
            if (result != null) {
                navigate()
            }
        }

    }

    fun loginDemoMode(mode: Int, navigate: () -> Unit) {
        viewModelScope.launch {
            when (mode) {
                1 -> {
                    val result = withContext(Dispatchers.IO) {
                        authService.initSession(
                            "contacto@fjmarlop.es",
                            "Admin1"
                        )
                    }
                    if (result != null) {
                        navigate()
                    }
                }

                2 -> {
                    val result = withContext(Dispatchers.IO) {
                        authService.initSession(
                            "empleado@fjmarlop.es",
                            "Admin1"
                        )
                    }
                    if (result != null) {
                        navigate()
                    }
                }
            }
        }
    }
}