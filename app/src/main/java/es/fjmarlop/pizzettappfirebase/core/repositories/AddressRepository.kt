package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import es.fjmarlop.pizzettappfirebase.entidades.response.AddressResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val address: Class<AddressResponse>
) : CrudRepository<AddressResponse>(firestore, address) {


    suspend fun insertAddress(address: AddressResponse, idClient: String): Boolean {
        return try {

            val newAddress = hashMapOf(
                DatabaseValues.Fields.ID_ADDRESS to address.idAddress,
                DatabaseValues.Fields.ID_CLIENT_ADDRESS to address.idClientAddress,
                DatabaseValues.Fields.NAME_ADDRESS to address.nameAddress,
                DatabaseValues.Fields.STREET_ADDRESS to address.streetAddress,
                DatabaseValues.Fields.PROVINCE_ADDRESS to address.provinceAddress,
                DatabaseValues.Fields.CITY_ADDRESS to address.cityAddress,
                DatabaseValues.Fields.POSTAL_CODE_ADDRESS to address.postalCodeAddress,
                DatabaseValues.Fields.FAV_ADDRESS to address.favAddress
            )

            insertInSubCollection(DatabaseValues.Collections.CLIENTS, DatabaseValues.Fields.ID_CLIENT, idClient, DatabaseValues.Collections.ADDRESS, newAddress)

        } catch (e: Exception) {
            false
        }
    }

    suspend fun getAddressesClient(idClient: String): Flow<List<AddressResponse>> {

        val idDoc = firestore.collection(DatabaseValues.Collections.CLIENTS).whereEqualTo(DatabaseValues.Fields.ID_CLIENT, idClient).get().await().documents[0].id

        return firestore.collection(DatabaseValues.Collections.CLIENTS)
            .document(idDoc)
            .collection(DatabaseValues.Collections.ADDRESS)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.mapNotNull { document -> document.toObject(AddressResponse::class.java) }
            }
    }

    suspend fun deleteAddress(idAddress: String, idClient: String): Boolean {

        val idDoc = firestore.collection(DatabaseValues.Collections.CLIENTS).whereEqualTo(DatabaseValues.Fields.ID_CLIENT, idClient).get().await().documents[0].id

        val querySnapshot =
            firestore.collection(DatabaseValues.Collections.CLIENTS)
                .document(idDoc)
                .collection(DatabaseValues.Collections.ADDRESS)
                .whereEqualTo(DatabaseValues.Fields.ID_ADDRESS, idAddress)
                .get()
                .await()

        if (querySnapshot.isEmpty) return false
        for (document in querySnapshot) {
            document.reference.delete()
        }
        return true
    }

    suspend fun updateFav(idAddress: String, idClient: String): Boolean {

        val idDoc = firestore.collection(DatabaseValues.Collections.CLIENTS).whereEqualTo(DatabaseValues.Fields.ID_CLIENT, idClient).get().await().documents[0].id

        val querySnapshot = firestore
            .collection(DatabaseValues.Collections.CLIENTS)
            .document(idDoc)
            .collection(DatabaseValues.Collections.ADDRESS)
            .get()
            .await()

        for (document in querySnapshot) {
            document.reference
                .update(DatabaseValues.Fields.FAV_ADDRESS, false)
        }

        val querySnapshotFav = firestore
            .collection(DatabaseValues.Collections.CLIENTS)
            .document(idDoc)
            .collection(DatabaseValues.Collections.ADDRESS)
            .whereEqualTo(DatabaseValues.Fields.ID_ADDRESS, idAddress)
            .get()
            .await()

        if (querySnapshotFav.isEmpty) return false

        for (document in querySnapshotFav) {
            document.reference
                .update(DatabaseValues.Fields.FAV_ADDRESS, true)
        }

        return true
    }




}


