package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.TamanioResponse

data class TamanioModel(
    val idTamanio: String,
    val nameTamanio: String,
    val priceTamanio: Double,
    val categoryTamanio: String
){
    fun toResponse(): TamanioResponse {
        return TamanioResponse(
            idTamanio = idTamanio,
            nameTamanio = nameTamanio,
            priceTamanio = priceTamanio,
            categoryTamanio = categoryTamanio
        )
    }
}