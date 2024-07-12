package es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.data.SplashService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: AuthService,
    private val splashService: SplashService
) : ViewModel() {

    suspend fun checkDestination(): SplashDestination {
        return try {
            if (!isUserLogged()) {
                SplashDestination.LoginScreen
            } else if (isUserLogged() && isEmployee()) {
                SplashDestination.MainGestionScreen
            } else {
                SplashDestination.MainClienteScreen
            }
        } catch (e: Exception) {
            return SplashDestination.LoginScreen
        }
    }

    private fun isUserLogged() = auth.checkLoggedUser()

    private suspend fun isEmployee(): Boolean {


        val user = auth.getCurrentUser() ?: return false

        val userEmail = user.email

        val isEmployee = suspendCoroutine { continuation ->
            viewModelScope.launch (Dispatchers.IO) {
                val emp = splashService.findByEmailEmployee(userEmail!!)
                continuation.resume(emp)
            }

        }
        return isEmployee != null
    }

}
