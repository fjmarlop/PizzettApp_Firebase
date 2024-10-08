package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.mainProducto.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.NewProductoScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.BackTopBar
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.entidades.model.ProductModel
import es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.ui.ErrorMsg

@Composable
fun MainProductoScreen(navHost: NavHostController, viewModel: MainProductoViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.resetSearch()
        viewModel.getAllProducts()
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
                SearchBar(value = uiState.searchText) { viewModel.setTextSearch(it) }

                if (uiState.products.isEmpty()) {
                    ErrorMsg(msg = "No existen productos registrados")
                } else {
                    ProductList(products = uiState.products)
                }
            }
        }
    }
}


@Composable
fun ProductItem(product: ProductModel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = obtenerIniciales(product.nombreProducto).uppercase(),
                modifier = Modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Text(
            text = product.nombreProducto, fontSize = 16.sp, modifier = Modifier
                .weight(1f)
                .padding(start = dimensionResource(id = R.dimen.padding_large))
        )
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium))
        )
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large)))
}

fun obtenerIniciales(cadena: String): String {
    val palabras = cadena.split(" ")
    return if (palabras.size >= 2) {
        palabras[0].take(1) + palabras[1].take(1)
    } else {
        cadena.take(2)
    }
}

@Composable
fun ProductList(products: List<ProductModel>) {

    if (products.isEmpty()) {
        Text(text = "Vacio")
    } else {
        LazyColumn {
            items(products) { product ->
                ProductItem(product = product) {}
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
            value = value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = stringResource(id = R.string.search) + "...") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onValueChange(value) }),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            }
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