package es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.data.MainCategoriasService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainCategoriasDomain @Inject constructor(private val service : MainCategoriasService) {

    suspend fun getAllCategories(): Flow<List<CategoryModel>> {
        return service.getAllCategories().map { it.map { category -> category.toModel() } }
    }
}