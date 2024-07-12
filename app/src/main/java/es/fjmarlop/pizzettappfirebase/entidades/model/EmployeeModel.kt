package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse

data class EmployeeModel(
    val idEmployee: String,
    val emailEmployee: String,
    val nameEmployee: String,
    val firstNameEmployee: String
) {
    fun toResponse(): EmployeeResponse {
        return EmployeeResponse(
            idEmployee = idEmployee,
            emailEmployee = emailEmployee,
            nameEmployee = nameEmployee,
            firstNameEmployee = firstNameEmployee
        )
    }
}
