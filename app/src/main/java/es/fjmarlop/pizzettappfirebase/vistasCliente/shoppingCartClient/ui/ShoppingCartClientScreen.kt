package es.fjmarlop.pizzettappfirebase.vistasCliente.shoppingCartClient.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.core.navigation.DetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.HistoryClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.OffersClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.ShoppingCartClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.MainScaffoldView

@Composable
fun ShoppingCartClientScreen(navHost: NavHostController, viewModel: ShoppingCartClientViewModel) {

    MainScaffoldView(
        index = 2,
        onClickCuenta = { navHost.navigate(DetailClientScreenNav) },
        onClickOfertas = { navHost.navigate(OffersClientScreenNav) },
        onClickCarrito = { navHost.navigate(ShoppingCartClientScreenNav) },
        onClickMisPedidos = { navHost.navigate(HistoryClientScreenNav) },
        onClickInicio = { navHost.navigate(MainClientScreenNav) },
    ) {
        Text(text = "Carrito")
    }
}