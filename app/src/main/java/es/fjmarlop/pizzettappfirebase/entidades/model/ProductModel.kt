package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse

data class ProductModel (
    val idProducto: String,
    val nombreProducto: String,
    val descripcionProducto: String,
    val urlImgProducto: String,
    val categoria: String
){

    fun toResponse() = ProductResponse(
        idProducto = idProducto,
        nombreProducto = nombreProducto,
        descripcionProducto = descripcionProducto,
        urlImgProducto = urlImgProducto,
        categoria = categoria
    )
}


