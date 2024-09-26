package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.TamanioModel

data class TamanioResponse (
    val idTamanio: String = "",
    val nameTamanio: String = "",
    val priceTamanio: Double = 0.0,
    val categoryTamanio: String = ""
){

    fun toModel(): TamanioModel {
        return TamanioModel(
            idTamanio = idTamanio,
            nameTamanio = nameTamanio,
            priceTamanio = priceTamanio,
            categoryTamanio = categoryTamanio
        )
    }
}