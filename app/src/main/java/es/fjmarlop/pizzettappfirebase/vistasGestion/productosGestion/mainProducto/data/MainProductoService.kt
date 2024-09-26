package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.data

import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.ProductRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainProductoService @Inject constructor(
    private val repository: ProductRepository,
) {

    suspend fun getAllProducts(): Flow<List<ProductResponse>> {
        return repository.getAll(DatabaseValues.Collections.PRODUCTS)
    }

}