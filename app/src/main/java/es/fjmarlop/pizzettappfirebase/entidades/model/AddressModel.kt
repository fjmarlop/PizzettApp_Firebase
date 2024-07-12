package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.AddressResponse

data class AddressModel(

    val idAddress: String,
    val nameAddress: String,
    val idClientAddress: String,
    val streetAddress: String,
    val provinceAddress: String,
    val cityAddress: String,
    val postalCodeAddress: String,
    val favAddress: Boolean
) {
    fun toResponse(): AddressResponse =
        AddressResponse(
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

