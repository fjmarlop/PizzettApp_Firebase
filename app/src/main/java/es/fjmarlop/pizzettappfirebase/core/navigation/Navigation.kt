package es.fjmarlop.pizzettappfirebase.core.navigation


import SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen.AddressesClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen.AddressesClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient.ExtendedDetailClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient.ExtendedDetailClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.helpClient.HelpClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.helpClient.HelpClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient.DetailClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient.DetailClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.historyClient.ui.HistoryClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.historyClient.ui.HistoryClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui.MainClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui.MainClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.offersClient.ui.OffersClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.offersClient.ui.OffersClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.shoppingCartClient.ui.ShoppingCartClientScreen
import es.fjmarlop.pizzettappfirebase.vistasCliente.shoppingCartClient.ui.ShoppingCartClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount.CreateAccountScreen
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount.CreateAccountViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.mainLogin.LoginScreen
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.mainLogin.LoginViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.recoveryPassword.RecoveryPasswordScreen
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.recoveryPassword.RecoveryPasswordViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail.SignInMailScreen
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail.SignInMailViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui.SplashViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui.MainManagementScreen
import es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui.MainManagementViewModel

@Composable
fun Browser(
    splashViewModel: SplashViewModel,
    loginViewModel: LoginViewModel,
    signInMailViewModel: SignInMailViewModel,
    createAccountViewModel: CreateAccountViewModel,
    recoveryPasswordViewModel: RecoveryPasswordViewModel,
    mainClientViewModel: MainClientViewModel,
    mainManagementViewModel: MainManagementViewModel,
    detailClientViewModel: DetailClientViewModel,
    historyClientViewModel: HistoryClientViewModel,
    offersClientViewModel: OffersClientViewModel,
    shoppingCartClientViewModel: ShoppingCartClientViewModel,
    extendedDetailClientViewModel: ExtendedDetailClientViewModel,
    addressesClientViewModel: AddressesClientViewModel,
    helpClientViewModel: HelpClientViewModel
) {

    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = SplashScreenNav) {

        composable<SplashScreenNav> {
            SplashScreen(navHost = navigationController, viewModel = splashViewModel)
        }

        composable<LoginScreenNav> {
            LoginScreen(navHost = navigationController, viewModel = loginViewModel)
        }

        composable<SingInMailScreenNav> {
            SignInMailScreen(navHost = navigationController, viewModel = signInMailViewModel)
        }

        composable<CreateAccountScreenNav> {
            CreateAccountScreen(navHost = navigationController, viewModel = createAccountViewModel)
        }

        composable<RecoveryPasswordScreenNav> {
            RecoveryPasswordScreen(navHost = navigationController, viewModel = recoveryPasswordViewModel)
        }

        composable<MainClientScreenNav> {
            MainClientScreen(navHost = navigationController, viewModel = mainClientViewModel)
        }

        composable<MainManagementScreenNav> {
            MainManagementScreen(navHost = navigationController, viewModel = mainManagementViewModel)
        }

        composable<DetailClientScreenNav> {
            DetailClientScreen(navHost = navigationController, viewModel = detailClientViewModel)
        }

        composable<HistoryClientScreenNav> {
            HistoryClientScreen(navHost = navigationController, viewModel = historyClientViewModel)
        }

        composable<OffersClientScreenNav> {
            OffersClientScreen(navHost = navigationController, viewModel = offersClientViewModel)
        }

        composable<ShoppingCartClientScreenNav> {
            ShoppingCartClientScreen(navHost = navigationController, viewModel = shoppingCartClientViewModel)
        }

        composable<ExtendedDetailClientScreenNav> {
            ExtendedDetailClientScreen(navHost = navigationController, viewModel = extendedDetailClientViewModel)
        }

        composable<AddressesClientScreenNav> {
            val idClient = it.toRoute<AddressesClientScreenNav>().idClient
            AddressesClientScreen(navHost = navigationController, viewModel = addressesClientViewModel, idClient = idClient)
        }

        composable<HelpClientScreenNav> {
            HelpClientScreen(navHost = navigationController, viewModel = helpClientViewModel)
        }

        composable<PantallaConArgumentosNav> { navBackStackEntry ->
            val argumento = navBackStackEntry.toRoute<PantallaConArgumentosNav>()
            // PantallaConArgumentos(navHost = navigationController, argumento = argumento, viewModel = viewModel)
        }

    }
}


