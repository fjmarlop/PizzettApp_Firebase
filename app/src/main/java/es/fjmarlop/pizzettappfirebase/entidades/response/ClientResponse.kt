package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel

data class ClientResponse(
    val idClient: String = "",
    val emailClient: String = "",
    val aliasClient: String = "",
    val nameClient: String = "",
    val phoneClient: String = "",
    val imageClient: String = "",
    val pointsClient: Int = 0
) {

    fun toModel() = ClientModel(
        idClient = idClient,
        emailClient = emailClient,
        aliasClient = aliasClient,
        nameClient = nameClient,
        phoneClient = phoneClient,
        imageClient = imageClient,
        pointsClient = pointsClient
    )
}
