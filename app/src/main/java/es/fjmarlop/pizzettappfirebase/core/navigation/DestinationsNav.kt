package es.fjmarlop.pizzettappfirebase.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object SplashScreenNav

@Serializable
object LoginScreenNav

@Serializable
object SingInMailScreenNav

@Serializable
object CreateAccountScreenNav

@Serializable
object RecoveryPasswordScreenNav

@Serializable
object MainClientScreenNav

@Serializable
object MainManagementScreenNav

@Serializable
object DetailClientScreenNav

@Serializable
object HistoryClientScreenNav

@Serializable
object OffersClientScreenNav

@Serializable
object ShoppingCartClientScreenNav

@Serializable
object ExtendedDetailClientScreenNav

@Serializable
data class AddressesClientScreenNav(
    val idClient: String
)

@Serializable
object HelpClientScreenNav

@Serializable
object MainProductoScreenNav

@Serializable
object NewProductoScreenNav

@Serializable
data class PantallaConArgumentosNav(
    val argumento: String
)
