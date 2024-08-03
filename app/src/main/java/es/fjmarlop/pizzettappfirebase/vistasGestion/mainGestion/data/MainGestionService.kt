package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.data

import es.fjmarlop.pizzettappfirebase.core.repositories.DatabaseValues
import es.fjmarlop.pizzettappfirebase.core.repositories.EmployeeRepository
import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.data.AuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainGestionService @Inject constructor(
    private val repository: EmployeeRepository,
    private val authService: AuthService
) {

    suspend fun getEmployee(): Flow<EmployeeResponse?> {
        val email = authService.getCurrentUser()!!.email
        return repository.getByField(DatabaseValues.Collections.EMPLOYEE, DatabaseValues.Fields.EMAIL_EMPLOYEE, email!!)
    }

}
