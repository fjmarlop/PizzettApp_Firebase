package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel

data class ProductResponse(
    val idProduct: String,
    val nameProduct: String,
    val descriptionProduct: String,
    val urlImgProduct: String
) {

    fun toModel() = ProductModel(
        idProduct = idProduct,
        nameProduct = nameProduct,
        descriptionProduct = descriptionProduct,
        urlImgProduct = urlImgProduct
    )
}
