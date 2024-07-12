package es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui

sealed class SplashDestination {

   data object LoginScreen: SplashDestination()

   data object MainClienteScreen: SplashDestination()

   data object MainGestionScreen: SplashDestination()
}