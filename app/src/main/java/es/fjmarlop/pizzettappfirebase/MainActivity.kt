package es.fjmarlop.pizzettappfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.fjmarlop.pizzettappfirebase.core.navigation.Browser
import es.fjmarlop.pizzettappfirebase.core.ui.theme.AppTheme
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen.AddressesClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient.ExtendedDetailClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.helpClient.HelpClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient.DetailClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.historyClient.ui.HistoryClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui.MainClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.offersClient.ui.OffersClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasCliente.shoppingCartClient.ui.ShoppingCartClientViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount.CreateAccountViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.mainLogin.LoginViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.recoveryPassword.RecoveryPasswordViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail.SignInMailViewModel
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui.SplashViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui.MainManagementViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.ui.MainProductoViewModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.ui.NewProductoViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val signInMailViewModel: SignInMailViewModel by viewModels()
    private val createAccountViewModel: CreateAccountViewModel by viewModels()
    private val recoveryPasswordViewModel: RecoveryPasswordViewModel by viewModels()
    private val mainClientViewModel: MainClientViewModel by viewModels()
    private val mainManagementViewModel: MainManagementViewModel by viewModels()
    private val detailClientViewModel: DetailClientViewModel by viewModels()
    private val historyClientViewModel: HistoryClientViewModel by viewModels()
    private val offersClientViewModel: OffersClientViewModel by viewModels()
    private val shoppingCartClientViewModel: ShoppingCartClientViewModel by viewModels()
    private val extendedDetailClientViewModel: ExtendedDetailClientViewModel by viewModels()
    private val addressesClientViewModel: AddressesClientViewModel by viewModels()
    private val helpClientViewModel: HelpClientViewModel by viewModels()
    private val mainProductoViewModel: MainProductoViewModel by viewModels()
    private val newProductoViewModel: NewProductoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Browser(
                    splashViewModel = splashViewModel,
                    loginViewModel = loginViewModel,
                    signInMailViewModel = signInMailViewModel,
                    createAccountViewModel = createAccountViewModel,
                    recoveryPasswordViewModel = recoveryPasswordViewModel,
                    mainClientViewModel = mainClientViewModel,
                    mainManagementViewModel = mainManagementViewModel,
                    detailClientViewModel = detailClientViewModel,
                    historyClientViewModel = historyClientViewModel,
                    offersClientViewModel = offersClientViewModel,
                    shoppingCartClientViewModel = shoppingCartClientViewModel,
                    extendedDetailClientViewModel = extendedDetailClientViewModel,
                    addressesClientViewModel = addressesClientViewModel,
                    helpClientViewModel = helpClientViewModel,
                    mainProductoViewModel = mainProductoViewModel,
                    newProductoViewModel = newProductoViewModel
                )
            }
        }
    }
}



