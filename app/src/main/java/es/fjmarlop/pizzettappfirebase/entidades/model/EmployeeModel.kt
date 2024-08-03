package es.fjmarlop.pizzettappfirebase.entidades.model

import es.fjmarlop.pizzettappfirebase.entidades.response.EmployeeResponse

data class EmployeeModel(
    val idEmpleado: String,
    val emailEmpleado: String,
    val nombreEmpleado: String,
    val apellidosEmpleado: String
) {
    fun toResponse(): EmployeeResponse {
        return EmployeeResponse(
            idEmpleado = idEmpleado,
            emailEmpleado = emailEmpleado,
            nombreEmpleado = nombreEmpleado,
            apellidosEmpleado = apellidosEmpleado
        )
    }
}
