package es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.ui

import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel

data class MainCategoriasUiState(
    val categorias : List<CategoryModel> = emptyList()
)
