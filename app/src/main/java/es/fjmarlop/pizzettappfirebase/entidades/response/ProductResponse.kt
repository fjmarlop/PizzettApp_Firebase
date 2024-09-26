package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel

data class ProductResponse(
    val idProducto: String = "",
    val nombreProducto: String = "",
    val descripcionProducto: String = "",
    val urlImgProducto: String = "",
    val categoria: String = ""
) {

    fun toModel() = ProductModel(
        idProducto = idProducto,
        nombreProducto = nombreProducto,
        descripcionProducto = descripcionProducto,
        urlImgProducto = urlImgProducto,
        categoria = categoria
    )
}
