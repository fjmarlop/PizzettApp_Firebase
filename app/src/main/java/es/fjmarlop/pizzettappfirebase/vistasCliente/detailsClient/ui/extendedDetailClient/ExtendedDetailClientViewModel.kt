package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.domain.DetailClientDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ExtendedDetailClientViewModel @Inject constructor(
    private val domain: DetailClientDomain,
    private val utils: Utils
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExtendedDetailClientUiState())
    val uiState: StateFlow<ExtendedDetailClientUiState> = _uiState

    suspend fun getClient(): ClientModel {
        val client = domain.getClient()
        client.collect {
            _uiState.value = _uiState.value.copy(client = it)
        }
        return client.first()
    }

    /** SETTERS **/
    fun setTextImageClient(route: String) {
        _uiState.value = _uiState.value.copy(newImageUrl = route)
    }

    fun setAliasClient(alias: String) {
        _uiState.value = _uiState.value.copy(newAlias = alias)
    }

    fun setNameClient(name: String) {
        _uiState.value = _uiState.value.copy(newName = name)
    }

    fun setPhoneClient(phone: String) {
        _uiState.value = _uiState.value.copy(newPhone = phone)
    }

    /** UPDATES **/
    fun updateImageUrlClient(newImageUrl: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { domain.updateImageUrlClient(newImageUrl, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }

    fun updateAliasClient(newAlias: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { domain.updateAliasClient(newAlias, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }

    fun updateNameClient(newName: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { domain.updateNameClient(newName, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }

    fun updatePhoneClient(newPhone: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result =
                withContext(Dispatchers.IO) { domain.updatePhoneClient(newPhone, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }

    /** RESETS **/
    fun resetNewImageUrlValue() {
        _uiState.value = _uiState.value.copy(newImageUrl = "")
    }

    fun resetNewAliasValue() {
        _uiState.value = _uiState.value.copy(newAlias = "")
    }

    fun resetNewNameValue() {
        _uiState.value = _uiState.value.copy(newName = "")
    }

    fun resetNewPhoneValue() {
        _uiState.value = _uiState.value.copy(newPhone = "")
    }


}