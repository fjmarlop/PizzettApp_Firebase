package es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui

import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel

data class MainClientUiState(
    val client: ClientModel? = null,
    val categories: List<CategoryModel> = listOf()
)
