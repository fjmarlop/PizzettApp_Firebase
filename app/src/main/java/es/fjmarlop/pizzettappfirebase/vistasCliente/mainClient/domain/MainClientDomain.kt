package es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel
import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse
import es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.data.MainClientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainClientDomain @Inject constructor(
    private val service: MainClientService
) {

    suspend fun createClient(cliente: ClientModel): Boolean {
        return service.createClient(cliente.toResponse())
    }

    suspend fun findClient(idCliente: String): Boolean {
        return service.findClientById(idCliente)
    }

    suspend fun getClient(idCliente: String): Flow<ClientModel> {
        return service.getClient(idCliente).map { it?.toModel() ?: ClientResponse().toModel() }
    }

    suspend fun getAllCategories(): Flow<List<CategoryModel>> {
        return service.getAllCategories().map { it.map { category -> category.toModel() } }
    }
}