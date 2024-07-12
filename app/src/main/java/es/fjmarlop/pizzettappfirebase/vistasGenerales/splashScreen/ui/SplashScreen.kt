
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui.SplashDestination
import es.fjmarlop.pizzettappfirebase.vistasGenerales.splashScreen.ui.SplashViewModel
import es.fjmarlop.pizzettappfirebase.core.navigation.LoginScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainManagementScreenNav

@Composable
fun SplashScreen(navHost: NavHostController, viewModel: SplashViewModel) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.pizzabg),
            contentDescription = "BackGround App",
            Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        CircularProgressIndicator(modifier = Modifier.padding(bottom = 150.dp), color = Color.White)
    }

    LaunchedEffect(true) {
        val destino = viewModel.checkDestination()

        when (destino) {
            SplashDestination.LoginScreen -> navHost.navigate(LoginScreenNav)
            SplashDestination.MainClienteScreen -> navHost.navigate(MainClientScreenNav)
            SplashDestination.MainGestionScreen -> navHost.navigate(MainManagementScreenNav)
        }

    }

}