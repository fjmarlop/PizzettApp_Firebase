package es.fjmarlop.pizzettappfirebase.core.repositories

object DatabaseValues {

    object Collections {
        const val CATEGORIES = "Categories"
        const val CLIENTS = "Clients"
        const val EMPLOYEE = "Empleados"
        const val ADDRESS = "addresses"

    }

    object Fields {

        const val SURNAME_EMPLOYEE = "apellidoEmpleado"
        const val EMAIL_EMPLOYEE = "emailEmpleado"
        const val NAME_EMPLOYEE = "nombreEmpleado"
        const val ID_EMPLOYEE = "idEmpleado"

        const val ID_CATEGORIES = "idCategory"
        const val NAME_CATEGORIES = "nameCategory"
        const val ORDER_CATEGORIES = "orderCategory"
        const val IMAGE_CATEGORIES = "urlImageCategory"

        const val ALIAS_CLIENT = "aliasClient"
        const val EMAIL_CLIENT = "emailClient"
        const val ID_CLIENT = "idClient"
        const val IMAGE_CLIENT = "imageClient"
        const val NAME_CLIENT = "nameClient"
        const val PHONE_CLIENT = "phoneClient"
        const val POINTS_CLIENT = "pointsClient"

        const val ID_ADDRESS = "idAddress"
        const val ID_CLIENT_ADDRESS = "idClientAddress"
        const val NAME_ADDRESS = "nameAddress"
        const val STREET_ADDRESS = "streetAddress"
        const val PROVINCE_ADDRESS = "provinceAddress"
        const val CITY_ADDRESS = "cityAddress"
        const val POSTAL_CODE_ADDRESS = "postalCodeAddress"
        const val FAV_ADDRESS = "favAddress"
    }
}