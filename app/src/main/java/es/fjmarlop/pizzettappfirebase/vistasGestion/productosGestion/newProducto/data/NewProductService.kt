package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.data

import es.fjmarlop.pizzettappfirebase.core.repositories.ProductRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse
import javax.inject.Inject

class NewProductService @Inject constructor(
    private val newProductRepository: ProductRepository
) {

    suspend fun createProduct(product: ProductResponse): Boolean {
        return newProductRepository.createProduct(product)
    }
}