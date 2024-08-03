package es.fjmarlop.pizzettappfirebase.entidades.response

import es.fjmarlop.pizzettappfirebase.entidades.model.EmployeeModel

data class EmployeeResponse(
    val idEmpleado: String = "",
    val emailEmpleado: String = "",
    val nombreEmpleado: String = "",
    val apellidosEmpleado: String = ""
) {

    fun toModel() = EmployeeModel(
        idEmpleado = idEmpleado,
        emailEmpleado = emailEmpleado,
        nombreEmpleado = nombreEmpleado,
        apellidosEmpleado = apellidosEmpleado
    )

}
