package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.LoginScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun MainManagementScreen(navHost: NavHostController, viewModel: MainManagementViewModel) {

    Queso()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        contentAlignment = Alignment.Center
    ) {

        Column {
            Text(text = "Main Gestión")
            Button(onClick = {
                viewModel.logOut() {
                    navHost.popBackStack()
                    navHost.navigate(LoginScreenNav)
                }
            }) {
                Text(text = "Cerrar sesión")
            }
        }

    }
}