package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.data.MainProductoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainProductoDomain @Inject constructor(
    private val service: MainProductoService
) {

    suspend fun getAllProducts(): Flow<List<ProductModel>> {
        return service.getAllProducts().map { product -> product.map { it.toModel() } }
    }
}