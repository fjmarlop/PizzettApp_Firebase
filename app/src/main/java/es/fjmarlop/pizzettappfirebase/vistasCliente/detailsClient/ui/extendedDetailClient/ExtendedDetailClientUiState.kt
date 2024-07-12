package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient

import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel

data class ExtendedDetailClientUiState(
    val client: ClientModel? = null,
    val newImageUrl: String = "",
    val newAlias: String = "",
    val newName: String = "",
    val newPhone: String = ""
)

