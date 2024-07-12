package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.AddressModel
import es.fjmarlop.pizzettappfirebase.entidades.model.ClientModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.data.DetailClientService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailClientDomain @Inject constructor(
    private val detailClientService: DetailClientService
) {

    suspend fun getClient(): Flow<ClientModel> {
        return detailClientService.getClient().map { response -> response!!.toModel() }
    }

    fun currentUser() = detailClientService.currentUser()

    fun logOut() = detailClientService.logOut()

    suspend fun deleteAccount(idClient: String): Boolean {
        if (idClient.isEmpty()) return false
        return detailClientService.deleteAccount(idClient)
    }

    suspend fun updateImageUrlClient(newImageUrl: String, idClient: String): Boolean {
        if (newImageUrl.isEmpty() || idClient.isEmpty()) return false
        return detailClientService.updateImageUrlClient(newImageUrl, idClient)
    }

    suspend fun updateAliasClient(newAlias: String, idClient: String): Boolean {
        if (newAlias.isEmpty() || idClient.isEmpty()) return false
        return detailClientService.updateAliasClient(newAlias, idClient)
    }

    suspend fun updateNameClient(newName: String, idClient: String): Boolean {
        if (newName.isEmpty() || idClient.isEmpty()) return false
        return detailClientService.updateNameClient(newName, idClient)
    }

    suspend fun updatePhoneClient(newPhone: String, idClient: String): Boolean {
        if (newPhone.isEmpty() || idClient.isEmpty()) return false
        return detailClientService.updatePhoneClient(newPhone, idClient)
    }

    suspend fun saveNewAddressClient(newAddress: AddressModel, idClient: String): Boolean {
        return detailClientService.saveNewAddressClient(newAddress.toResponse(), idClient)
    }

    suspend fun getAddressesClient(idClient: String): Flow<List<AddressModel>> {
        return detailClientService.getAddressesClient(idClient).map { addresses ->
            addresses.map { address -> address.toModel() }
        }
    }

    suspend fun deleteAddressClient(idAddress: String, idClient: String): Boolean {
        if (idAddress.isEmpty()) return false
        return detailClientService.deleteAddressClient(idAddress, idClient)
    }

    suspend fun doFav(idAddress: String, idClient: String): Boolean {
        if (idAddress.isEmpty()) return false
     return  detailClientService.doFav(idAddress, idClient)
    }
}