package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen

import es.fjmarlop.pizzettappfirebase.entidades.model.AddressModel

data class AddressesClientUiState(
    val addressList: List<AddressModel> = emptyList(),
    val nameNewAddress: String = "",
    val streetNewAddress: String = "",
    val numberNewAddress: String = "",
    val stairsNewAddress: String = "",
    val floorNewAddress: String = "",
    val doorNewAddress: String = "",
    val provinceNewAddress: String = "",
    val cityNewAddress: String = "",
    val postalCodeNewAddress: String = ""
)
