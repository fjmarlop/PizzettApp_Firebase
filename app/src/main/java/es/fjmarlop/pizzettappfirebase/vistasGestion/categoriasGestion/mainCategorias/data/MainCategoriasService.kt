package es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.data

import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.CategoriesRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainCategoriasService @Inject constructor(
    private val repository: CategoriesRepository
) {

    suspend fun getAllCategories(): Flow<List<CategoryResponse>> {
        return repository.getAllOrderBy(DatabaseValues.Collections.CATEGORIES, DatabaseValues.Fields.ORDER_CATEGORIES)
    }
}