package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel

data class CategoryResponse(
    val idCategory: String = "",
    val nameCategory: String = "",
    val urlImageCategory: String = "",
    val order: Int = 0
){
    fun toModel() = CategoryModel(
        idCategory = idCategory,
        nameCategory = nameCategory,
        urlImageCategory = urlImageCategory,
        order = order
    )
}
