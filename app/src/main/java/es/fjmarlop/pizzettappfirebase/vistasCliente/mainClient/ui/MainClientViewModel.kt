package es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.domain.MainClientDomain
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainClientViewModel @Inject constructor(
    private val auth: AuthService,
    private val domain: MainClientDomain
) : ViewModel() {


    private val _uiState = MutableStateFlow(MainClientUiState())
    val uiState: StateFlow<MainClientUiState> = _uiState


    suspend fun paintMainScreen() {
        doClient()
        doCategories()
    }

    private suspend fun doCategories() {
        domain.getAllCategories().collect { categories ->
            _uiState.update { uiState ->
                uiState.copy(
                    categories = categories
                )
            }
        }
    }


    private fun doClient() {
        viewModelScope.launch {
            val currentUser = auth.getCurrentUser()

            if (currentUser != null) {

                if (currentUser.isAnonymous) {
                    _uiState.value = _uiState.value.copy(
                        client = isAnonymous(currentUser)
                    )
                } else {
                    //Comprobamos si el usuario ya existe
                    val exitClient =
                        withContext(Dispatchers.IO) { domain.findClient(currentUser.uid) }

                    //Si no existe lo creamos
                    if (!exitClient) {
                        withContext(Dispatchers.IO) {
                            domain.createClient(newClient(currentUser))
                        }

                        //Cargamos el cliente al uiState
                        getClient(currentUser)
                    } else {
                        //Cargamos el cliente al uiState
                        getClient(currentUser)
                    }
                }
            }
        }
    }

    private suspend fun getClient(currentUser: FirebaseUser): ClientModel {
        val clientCollect = domain.getClient(currentUser.uid)
        clientCollect.collect { client ->
            _uiState.value = _uiState.value.copy(
                client = client
            )
        }
        return clientCollect.first()
    }


    private fun isAnonymous(currentUser: FirebaseUser): ClientModel {
        return ClientModel(
            idClient = currentUser.uid,
            nameClient = "Invitado",
            emailClient = "",
            phoneClient = "",
            aliasClient = "Invitado",
            imageClient = "",
            pointsClient = 0
        )
    }

    private fun newClient(currentUser: FirebaseUser): ClientModel {
        return ClientModel(
            idClient = currentUser.uid,
            nameClient = currentUser.displayName ?: "",
            emailClient = currentUser.email!!,
            phoneClient = currentUser.phoneNumber ?: "",
            aliasClient = "",
            imageClient = currentUser.photoUrl.toString() ?: "",
            pointsClient = 0
        )
    }

}