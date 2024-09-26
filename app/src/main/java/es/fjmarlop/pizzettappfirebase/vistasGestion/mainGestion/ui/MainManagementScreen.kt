package es.fjmarlop.pizzettappfirebase.vistasGestion.mainGestion.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.LoginScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainCategoriasScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainProductoScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainManagementScreen(navHost: NavHostController, viewModel: MainManagementViewModel) {

    val name by viewModel.name.collectAsState()
    val firstName by viewModel.firstName.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getEmployee()
    }

    Scaffold { paddingValues ->
        Pizza()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {
                HeaderManagement(name = name, firstName = firstName) {
                    viewModel.logOut() {
                        navHost.popBackStack()
                        navHost.navigate(LoginScreenNav)
                    }
                }
                HorizontalDivider(Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium)))

                ItemManagement(
                    title = stringResource(id = R.string.titleCategories),
                    image = Icons.Default.Category
                ) {
                    navHost.navigate(
                        MainCategoriasScreenNav
                    )
                }
                ItemManagement(
                    title = stringResource(id = R.string.titleProducts),
                    image = Icons.Default.LocalPizza
                ) {
                    navHost.navigate(MainProductoScreenNav)
                }
                ItemManagement(title = stringResource(id = R.string.titleTamanios), image = Icons.Default.Architecture) {

                }
            }
        }
    }
}


@Composable
fun HeaderManagement(name: String, firstName: String, onClickButton: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .size(35.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.titleManagement),
                style = MaterialTheme.typography.titleMedium,
            )
            Text("$name $firstName", fontSize = 12.sp)
        }
        IconButton(onClick = { onClickButton() }) {
            Icon(
                imageVector = Icons.Filled.PowerSettingsNew,
                contentDescription = "Session End",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ItemManagement(title: String, image: ImageVector, onClickItem: () -> Unit) {
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))

    Card(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
            .fillMaxWidth()
            .clickable { onClickItem() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = image, contentDescription = null, modifier = Modifier
                    .size(50.dp)
                    .padding(
                        dimensionResource(id = R.dimen.padding_small)
                    ), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontSize = 18.sp)
        }
    }


}