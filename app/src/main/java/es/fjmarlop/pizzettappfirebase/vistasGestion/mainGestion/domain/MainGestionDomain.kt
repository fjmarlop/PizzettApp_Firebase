package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.domain

import es.fjmarlop.pizzettappfirebase.entidades.model.EmployeeModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.data.MainGestionService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MainGestionDomain @Inject constructor(private val service: MainGestionService) {

    suspend fun getEmployee(): Flow<EmployeeModel>{
        return service.getEmployee().map { it?.toModel() ?: EmployeeModel("", "", "","") }
    }

}