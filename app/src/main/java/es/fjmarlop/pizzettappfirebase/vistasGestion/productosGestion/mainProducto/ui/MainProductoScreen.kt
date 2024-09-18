package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.NewProductoScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.BackTopBar
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza

@Composable
fun MainProductoScreen(navHost: NavHostController, viewModel: MainProductoViewModel) {

    val searchText by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            BackTopBar(title = stringResource(id = R.string.titleProducts)) {
                navHost.navigateUp()
            }
        },
        floatingActionButton = { NewProductFab { navHost.navigate(NewProductoScreenNav) } }
    ) { paddingValues ->
        Pizza()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchBar(value = searchText) {

                }
            }
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = value, onValueChange = { onValueChange(it) }, modifier = Modifier.weight(1f),
            placeholder = { Text(text = stringResource(id = R.string.search) + "...") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onValueChange(value) }),
            trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search))}
        )
    }
}


@Composable
fun NewProductFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.newProduct)) },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.newProduct)
            )
        },
        onClick = { onClick() },
        expanded = true,
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        modifier = Modifier.padding(16.dp)
    )
}