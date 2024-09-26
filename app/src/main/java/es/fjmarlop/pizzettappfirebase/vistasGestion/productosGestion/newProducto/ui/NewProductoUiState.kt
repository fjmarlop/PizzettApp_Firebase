package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.ui

import es.fjmarlop.pizzettappfirebase.entidades.model.TamanioModel

data class NewProductoUiState(
    val idProduct: String = "",
    val nombreProducto: String = "",
    val descriptionProduct: String = "",
    val urlImgProducto: String = "",
    val category: String = "",
    val categoriesList: List<String> = emptyList(),
    val tamaniosPvpList: List<TamanioModel> = emptyList()
)