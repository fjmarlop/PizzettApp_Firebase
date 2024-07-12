package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient

import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel

data class DetailClientUiState(
    val isLoading: Boolean = false,
    val client: ClientModel? = null,
    val isAnonymous: Boolean = false

)
