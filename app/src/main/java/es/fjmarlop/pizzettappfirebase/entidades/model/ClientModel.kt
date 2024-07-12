package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse

data class ClientModel(
    val idClient: String,
    val emailClient: String,
    val aliasClient: String,
    val nameClient: String,
    val phoneClient: String,
    val imageClient:String,
    val pointsClient: Int
){
    fun toResponse() = ClientResponse(
        idClient = idClient,
        emailClient = emailClient,
        aliasClient = aliasClient,
        nameClient = nameClient,
        phoneClient = phoneClient,
        imageClient = imageClient,
        pointsClient = pointsClient
    )
}
