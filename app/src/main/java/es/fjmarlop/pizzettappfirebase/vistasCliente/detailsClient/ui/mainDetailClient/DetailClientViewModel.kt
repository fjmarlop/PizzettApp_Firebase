package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.domain.DetailClientDomain
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailClientViewModel @Inject constructor(
    private val domain: DetailClientDomain
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailClientUiState())
    val uiState: StateFlow<DetailClientUiState> = _uiState

    suspend fun getClient(): ClientModel {

        resetUiState()

        val currentUser = domain.currentUser()

        if (currentUser != null) {
            if (currentUser.isAnonymous) {
                _uiState.value = _uiState.value.copy(isAnonymous = true)
            }
        }

        val client = domain.getClient()

        client.collect {
            _uiState.value = _uiState.value.copy(client = it)
        }

        return client.first()
    }


    fun logOut(navigateToLogin: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { domain.logOut() }
        navigateToLogin()
    }

    fun deleteAccount(idClient: String, navigate: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            domain.deleteAccount(idClient)
        }
        navigate()
    }

    private fun resetUiState() {
        _uiState.value = DetailClientUiState()
    }
}



