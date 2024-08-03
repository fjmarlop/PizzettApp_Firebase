package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.domain.MainGestionDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainManagementViewModel @Inject constructor(
    private val auth: AuthService, // eliminar mas tarde
    private val domain: MainGestionDomain
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName


    fun getEmployee() {
        viewModelScope.launch {
            val emp = withContext(Dispatchers.IO) { domain.getEmployee() }
            emp.collect{ response ->
                _name.value = response.nombreEmpleado
                _firstName.value = response.apellidosEmpleado
            }
        }
    }


    fun logOut(navigate: () -> Unit) {
        auth.logOut()
        viewModelScope.launch {
            navigate()
        }
    }

}