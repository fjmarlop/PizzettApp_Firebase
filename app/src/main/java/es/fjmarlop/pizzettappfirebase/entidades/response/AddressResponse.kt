package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.AddressModel

data class AddressResponse(

    val idAddress: String = "",
    val nameAddress: String = "",
    val idClientAddress: String = "",
    val streetAddress: String = "",
    val provinceAddress: String = "",
    val cityAddress: String = "",
    val postalCodeAddress: String = "",
    val favAddress: Boolean = false
) {

    fun toModel(): AddressModel =
        AddressModel(
            idAddress = idAddress,
            nameAddress = nameAddress,
            idClientAddress = idClientAddress,
            streetAddress = streetAddress,
            provinceAddress = provinceAddress,
            cityAddress = cityAddress,
            postalCodeAddress = postalCodeAddress,
            favAddress = favAddress
        )
}
