package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.data

import es.fjmarlop.pizzettappfirebase.core.repositories.AddressRepository
import es.fjmarlop.pizzettappfirebase.core.repositories.ClientRepository
import es.fjmarlop.pizzettappfirebase.core.DatabaseValues
import es.fjmarlop.pizzettappfirebase.entidades.response.AddressResponse
import es.fjmarlop.pizzettappfirebase.entidades.response.ClientResponse
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailClientService @Inject constructor(
    private val clientRepository: ClientRepository,
    private val addressRepository: AddressRepository,
    private val authService: AuthService
)  {



    suspend fun getClient(): Flow<ClientResponse?> {
        val idClient = authService.getCurrentUser()!!.uid
        return clientRepository.getById(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT)
    }

    fun currentUser() = authService.getCurrentUser()

    fun logOut() {
        authService.logOut()
    }

    suspend fun deleteAccount(idClient: String): Boolean {
        return clientRepository.delete(DatabaseValues.Collections.CLIENTS, DatabaseValues.Fields.ID_CLIENT, idClient) && authService.deleteAccount()
    }

    suspend fun updateImageUrlClient(newImageUrl: String, idClient: String): Boolean {
        return clientRepository.update(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT,  DatabaseValues.Fields.IMAGE_CLIENT,newImageUrl)
    }

    suspend fun updateAliasClient(newAlias: String, idClient: String): Boolean {
        return clientRepository.update(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT,  DatabaseValues.Fields.ALIAS_CLIENT, newAlias)
    }

    suspend fun updateNameClient(newName: String, idClient: String): Boolean {
       return clientRepository.update(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT,  DatabaseValues.Fields.NAME_CLIENT, newName)
    }

    suspend fun updatePhoneClient(newPhone: String, idClient: String): Boolean {
       return clientRepository.update(DatabaseValues.Collections.CLIENTS, idClient, DatabaseValues.Fields.ID_CLIENT,  DatabaseValues.Fields.PHONE_CLIENT, newPhone)
    }

    suspend fun saveNewAddressClient(newAddress: AddressResponse, idClient: String): Boolean {
        return addressRepository.insertAddress(newAddress, idClient)
    }

    suspend fun getAddressesClient(idClient: String): Flow<List<AddressResponse>> {
        return addressRepository.getAddressesClient(idClient)
    }

    suspend fun deleteAddressClient(idAddress: String, idClient: String): Boolean {
        return addressRepository.deleteAddress(idAddress, idClient)
    }

    suspend fun doFav(idAddress: String, idClient: String): Boolean {
        return addressRepository.updateFav(idAddress, idClient)
    }

}

