package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse

data class ProductModel (
    val idProduct: String,
    val nameProduct: String,
    val descriptionProduct: String,
    val urlImgProduct: String
){

    fun toResponse() = ProductResponse(
        idProduct = idProduct,
        nameProduct = nameProduct,
        descriptionProduct = descriptionProduct,
        urlImgProduct = urlImgProduct
    )
}


