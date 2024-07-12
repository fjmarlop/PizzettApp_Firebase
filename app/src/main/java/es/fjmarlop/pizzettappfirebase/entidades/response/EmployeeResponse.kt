package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.EmployeeModel

data class EmployeeResponse(
    val idEmployee: String = "",
    val emailEmployee: String = "",
    val nameEmployee: String = "",
    val firstNameEmployee: String = ""
) {

    fun toModel() = EmployeeModel(
        idEmployee = idEmployee,
        emailEmployee = emailEmployee,
        nameEmployee = nameEmployee,
        firstNameEmployee = firstNameEmployee
    )

}
