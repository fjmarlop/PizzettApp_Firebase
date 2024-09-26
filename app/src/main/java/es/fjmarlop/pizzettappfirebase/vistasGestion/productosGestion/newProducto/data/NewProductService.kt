package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.data

import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.CategoriesRepository
import es.fjmarlop.pizzettappfirebase.core.repositories.ProductRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.ProductResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewProductService @Inject constructor(
    private val newProductRepository: ProductRepository,
    private val catRepository: CategoriesRepository
) {

    suspend fun createProduct(product: ProductResponse): Boolean {
        return newProductRepository.createProduct(product)
    }

    suspend fun getCategories(): Flow<List<CategoryResponse>> {
        return catRepository.getAllOrderBy(DatabaseValues.Collections.CATEGORIES, DatabaseValues.Fields.ORDER_CATEGORIES)
    }

}