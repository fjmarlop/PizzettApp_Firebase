package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.mainRepository.CrudRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepository @Inject constructor(
    firestore: FirebaseFirestore,
    category: Class<CategoryResponse>
): CrudRepository<CategoryResponse>(firestore, category) {

    suspend fun getAllCategories(): Flow<List<CategoryResponse>> {
        return getAllOrderBy(DatabaseValues.Collections.CATEGORIES, DatabaseValues.Fields.ORDER_CATEGORIES)
    }
}
