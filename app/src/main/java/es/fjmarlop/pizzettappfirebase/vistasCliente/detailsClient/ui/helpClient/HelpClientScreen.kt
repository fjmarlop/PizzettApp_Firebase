package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.helpClient

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun HelpClientScreen(navHost: NavHostController, viewModel: HelpClientViewModel) {
    Queso()
    Pizza()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
//            .verticalScroll(scrollState, enabled = true)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        CloseButton {
            navHost.navigateUp()
        }
            Text(text = stringResource(id = R.string.help), style = MaterialTheme.typography.titleLarge)
        }
    }
}