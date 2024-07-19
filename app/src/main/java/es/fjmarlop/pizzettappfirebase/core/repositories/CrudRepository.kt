package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

open class CrudRepository<T>(
    private val firestore: FirebaseFirestore,
    private val clazz: Class<T>
) : IFirestoreRepository<T> {

    override suspend fun getAll(collection: String): Flow<List<T>> {
        return firestore
            .collection(collection)
            .snapshots()
            .map { qs ->
                qs.documents.mapNotNull { doc ->
                    doc.toObject(clazz)
                }
            }
    }

    override suspend fun getAllOrderBy(collection: String, field: String): Flow<List<T>> {
        return firestore
            .collection(collection)
            .orderBy(field, Query.Direction.ASCENDING)
            .snapshots()
            .map { qs ->
                qs.documents.mapNotNull { doc ->
                    doc.toObject(clazz)
                }
            }
    }


    override suspend fun getById(collection: String, id: String, fieldId: String): Flow<T?> {
        return firestore
            .collection(collection)
            .whereEqualTo(fieldId, id)
            .snapshots()
            .map { querySnapshot ->
                val doc = querySnapshot.documents.firstOrNull()
                doc?.toObject(clazz) ?: throw Exception("Error: Document not found")
            }.catch {
               flowOf(null)
            }
    }

    override suspend fun delete(collection: String, id: String, fieldId: String): Boolean {
        val querySnapshot =
            firestore.collection(collection)
                .whereEqualTo(fieldId, id)
                .get()
                .await()
        if (querySnapshot.isEmpty) return false
        for (document in querySnapshot) {
            document.reference.delete()
        }
        return true
    }

    override suspend fun update(
        collection: String,
        id: String,
        fieldId: String,
        fieldDatabase: String,
        value: String
    ): Boolean {
        val querySnapshot =
            firestore.collection(collection)
                .whereEqualTo(fieldId, id)
                .get()
                .await()
        if (querySnapshot.isEmpty) return false
        for (document in querySnapshot) {
            document.reference.update(fieldDatabase, value)
        }
        return true
    }

    override suspend fun update(
        collection: String,
        id: String,
        fieldId: String,
        fieldDatabase: String,
        value: Boolean
    ): Boolean {
        val querySnapshot =
            firestore.collection(collection)
                .whereEqualTo(fieldId, id)
                .get()
                .await()
        if (querySnapshot.isEmpty) return false
        for (document in querySnapshot) {
            document.reference.update(fieldDatabase, value)
        }
        return true
    }

    override suspend fun findById(collection: String, fieldId: String, id: String): Boolean {
        val querySnapshot =
            firestore.collection(collection)
                .whereEqualTo(fieldId, id)
                .get()
                .await()
        return !querySnapshot.isEmpty
    }

    override suspend fun insert(collection: String, item: Any): Boolean {
        val result = firestore
            .collection(collection)
            .add(item)
            .await()
        return result != null

    }

    override suspend fun insertInSubCollection(
        collection: String, fieldId: String, id: String,
        subCollection: String, item: Any
    ): Boolean {

        val idDoc =
            firestore.collection(collection).whereEqualTo(fieldId, id).get().await().documents[0].id

        val result = firestore
            .collection(collection)
            .document(idDoc)
            .collection(subCollection)
            .add(item)
            .await()

        return result != null
    }

}