package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.ui.theme.BackTopBar

@Composable
fun NewProductoScreen(navHost: NavHostController, viewModel: NewProductoViewModel) {

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.resetUiState()
    }

    Scaffold(topBar = { BackTopBar(title = stringResource(id = R.string.newProduct)) { navHost.navigateUp() } }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Column {
                NewProductText(
                    name = uiState.nombreProducto,
                    title = stringResource(id = R.string.name)
                ) { viewModel.setName(it) }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                NewProductText(
                    name = uiState.descriptionProduct,
                    title = stringResource(id = R.string.description)
                ) {
                    viewModel.setDescription(it)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                NewProductText(
                    name = uiState.urlImgProducto,
                    title = stringResource(id = R.string.urlImg)
                ) {
                    viewModel.setUrlImg(it)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))


                NewProductoSave { viewModel.createProduct("Error, no se ha podido crear el producto.") { navHost.navigateUp() } }
            }
        }
    }
}

@Composable
fun NewProductoSave(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large) * 3)
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(id = R.string.save)
            )
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
            Text(
                text = stringResource(id = R.string.save).uppercase(),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
fun NewProductText(name: String, title: String, nameChanged: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.minimal_space))
        )
        OutlinedTextField(
            value = name,
            onValueChange = { nameChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { }),
            placeholder = { Text(text = "$title del producto") }
        )
    }
}

