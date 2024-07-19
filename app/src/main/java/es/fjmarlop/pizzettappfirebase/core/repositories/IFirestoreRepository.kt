package es.fjmarlop.pizzettappfirebase.core.repositories

import kotlinx.coroutines.flow.Flow

interface IFirestoreRepository<T> {
    suspend fun getAll(collection: String): Flow<List<T>>

    suspend fun getAllOrderBy(collection: String, field: String): Flow<List<T>>

    suspend fun getById(collection: String, id: String, fieldId: String): Flow<T?>

    suspend fun insert(collection: String, item: Any): Boolean

    suspend fun delete(collection: String, id: String, fieldId: String): Boolean

    suspend fun update(collection: String, id: String,
                       fieldId: String, fieldDatabase: String, value: String): Boolean

    suspend fun update(collection: String, id: String,
                       fieldId: String, fieldDatabase: String, value: Boolean): Boolean

    suspend fun findById(collection: String, fieldId: String, id: String): Boolean

    suspend fun insertInSubCollection(collection: String, fieldId: String, id: String,
        subCollection: String, item: Any): Boolean
}