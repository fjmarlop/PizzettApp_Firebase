package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse

data class CategoryModel(
    val idCategory: String,
    val nameCategory: String,
    val urlImageCategory: String,
    val order: Int
) {
    fun toResponse() = CategoryResponse(
        idCategory = idCategory,
        nameCategory = nameCategory,
        urlImageCategory = urlImageCategory,
        order = order
    )

}
