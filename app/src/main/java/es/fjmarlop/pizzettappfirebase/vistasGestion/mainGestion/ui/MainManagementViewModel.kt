package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainManagementViewModel @Inject constructor(
    private val auth: AuthService,
) : ViewModel() {


    fun logOut(navigate: () -> Unit) {
        auth.logOut()
        viewModelScope.launch {
           navigate()
        }
    }

}