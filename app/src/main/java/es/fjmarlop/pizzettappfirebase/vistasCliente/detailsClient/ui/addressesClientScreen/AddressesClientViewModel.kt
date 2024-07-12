package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.fjmarlop.pizzettappfirebase.core.utils.Utils
import es.fjmarlop.pizzettappfirebase.entidades.model.AddressModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.domain.DetailClientDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddressesClientViewModel @Inject constructor(
    private val domain: DetailClientDomain,
    private val utils: Utils
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(AddressesClientUiState())
    val uiState: StateFlow<AddressesClientUiState> = _uiState

    fun setNameAddress(name: String) {
        _uiState.value = _uiState.value.copy(nameNewAddress = name)
    }

    fun setStreetAddress(street: String) {
        _uiState.value = _uiState.value.copy(streetNewAddress = street)
    }

    fun setNumberAddress(number: String) {
        _uiState.value = _uiState.value.copy(numberNewAddress = number)
    }

    fun setFloorAddress(floor: String) {
        _uiState.value = _uiState.value.copy(floorNewAddress = floor)
    }

    fun setDoorAddress(door: String) {
        _uiState.value = _uiState.value.copy(doorNewAddress = door)
    }

    fun setProvinceAddress(province: String) {
        _uiState.value = _uiState.value.copy(provinceNewAddress = province)
    }

    fun setCityAddress(city: String) {
        _uiState.value = _uiState.value.copy(cityNewAddress = city)
    }

    fun setPostalCodeAddress(postalCode: String) {
        _uiState.value = _uiState.value.copy(postalCodeNewAddress = postalCode)
    }

    fun setStairsAddress(stairs: String) {
        _uiState.value = _uiState.value.copy(stairsNewAddress = stairs)
    }

    fun enableSaveButton(): Boolean {
        return _uiState.value.nameNewAddress.isNotEmpty() &&
                _uiState.value.streetNewAddress.isNotEmpty() &&
                _uiState.value.numberNewAddress.isNotEmpty() &&
                _uiState.value.provinceNewAddress.isNotEmpty() &&
                _uiState.value.cityNewAddress.isNotEmpty() &&
                _uiState.value.postalCodeNewAddress.isNotEmpty()
    }

    fun saveNewAddress(
        idClient: String,
        msg: String,
        uiState: AddressesClientUiState,
        navigate: () -> Unit
    ) {

        val address = createAddress(uiState)

        val newAddress = AddressModel(
            idAddress = System.currentTimeMillis().toString(),
            idClientAddress = idClient,
            nameAddress = uiState.nameNewAddress,
            streetAddress = address,
            provinceAddress = uiState.provinceNewAddress,
            cityAddress = uiState.cityNewAddress,
            postalCodeAddress = uiState.postalCodeNewAddress,
            favAddress = false,
        )

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.saveNewAddressClient(newAddress, idClient) }
            if (!result) utils.msgToastShort(msg) else navigate()
        }
    }

    private fun createAddress(uiState: AddressesClientUiState): String {

        val num = if (uiState.numberNewAddress != "") " ${uiState.numberNewAddress}" else ""
        val stairs =
            if (uiState.stairsNewAddress != "") ", esc: ${uiState.stairsNewAddress}" else ""
        val floor = if (uiState.floorNewAddress != "") ", ${uiState.floorNewAddress}" else ""
        val door = if (uiState.doorNewAddress != "") ", ${uiState.doorNewAddress}" else ""

        return "${uiState.streetNewAddress} $num $stairs $floor $door "
    }

    fun getAddressesClient(idClient: String) {
        viewModelScope.launch {
            val addresses = withContext(Dispatchers.IO) { domain.getAddressesClient(idClient) }
            addresses.collect {
                _uiState.update { state ->
                    state.copy(addressList = it)
                }
            }
        }
    }

    fun resetNewAddress() {
        _uiState.value = AddressesClientUiState()
    }

    fun deleteAddress(idAddress: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.deleteAddressClient(idAddress, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }

    fun doFav(idAddress: String, idClient: String, msg: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { domain.doFav(idAddress, idClient) }
            if (!result) utils.msgToastShort(msg)
        }
    }
}