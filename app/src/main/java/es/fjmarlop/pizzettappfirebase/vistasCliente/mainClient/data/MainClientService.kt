package es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.data

import es.fjmarlop.pizzettappfirebase.core.repositories.CategoriesRepository
import es.fjmarlop.pizzettappfirebase.core.repositories.ClientRepository
import es.fjmarlop.pizzettappfirebase.core.repositories.DatabaseValues
import es.fjmarlop.pizzettappfirebase.entidades.response.CategoryResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainClientService @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val clientRepository: ClientRepository
)  {

    suspend fun getAllCategories(): Flow<List<CategoryResponse>> {
        return categoriesRepository.getAllCategories()
    }

    suspend fun findClientById(idClient: String): Boolean {
        return clientRepository.findById(DatabaseValues.Collections.CLIENTS, DatabaseValues.Fields.ID_CLIENT, idClient)
    }

    suspend fun getClient(idClient: String): Flow<ClientResponse?> {
        return clientRepository.getById(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT)
    }

    suspend fun createClient(client: ClientResponse): Boolean {
      return clientRepository.createClient(client)
    }


}