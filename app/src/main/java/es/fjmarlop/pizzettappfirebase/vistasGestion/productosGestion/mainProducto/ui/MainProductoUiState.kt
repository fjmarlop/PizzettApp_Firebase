package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.ui

import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel

data class MainProductoUiState (
    val products: List<ProductModel> = emptyList(),
    val searchText : String = ""
)