package es.fjmarlop.pizzettappfirebase.core.repositories

import com.google.firebase.firestore.FirebaseFirestore
import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClientRepository @Inject constructor(
    firestore: FirebaseFirestore,
    client: Class<ClientResponse>
) : CrudRepository<ClientResponse>(firestore, client) {


    suspend fun findClientById(idClient: String): Boolean {
        return findById(DatabaseValues.Collections.CLIENTS,DatabaseValues.Fields.ID_CLIENT,idClient)
    }

    suspend fun getClient(idClient: String): Flow<ClientResponse> {
        return getById(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT)
    }

    suspend fun createClient(client: ClientResponse): Boolean {
        return try {

            val newClient = hashMapOf(
                DatabaseValues.Fields.ID_CLIENT to client.idClient,
                DatabaseValues.Fields.NAME_CLIENT to client.nameClient,
                DatabaseValues.Fields.PHONE_CLIENT to client.phoneClient,
                DatabaseValues.Fields.EMAIL_CLIENT to client.emailClient,
                DatabaseValues.Fields.ALIAS_CLIENT to client.aliasClient,
                DatabaseValues.Fields.IMAGE_CLIENT to client.imageClient,
                DatabaseValues.Fields.POINTS_CLIENT to client.pointsClient
            )

            insert(DatabaseValues.Collections.CLIENTS, newClient)

        } catch (e: Exception) {
            false
        }
    }

}