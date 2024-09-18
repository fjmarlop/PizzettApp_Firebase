package es.fjmarlop.pizzettappfirebase.core.ui.theme

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Wysiwyg
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import es.fjmarlop.pizzettappfirebase.R

@Composable
fun MainScaffoldView(
    index: Int,
    onClickInicio: () -> Unit,
    onClickOfertas: () -> Unit,
    onClickCarrito: () -> Unit,
    onClickMisPedidos: () -> Unit,
    onClickCuenta: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        bottomBar = {
            MainBottomBar(
                index = index,
                onClickInicio = { onClickInicio() },
                onClickOfertas = { onClickOfertas() },
                onClickCarrito = { onClickCarrito() },
                onClickMisPedidos = { onClickMisPedidos() },
                onClickCuenta = { onClickCuenta() },
                cantidadLineas = 0
            )
        }
    )
    { contentPadding ->
        Queso()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            content()
        }
    }
}


@Composable
fun MainBottomBar(
    index: Int,
    onClickInicio: () -> Unit,
    onClickOfertas: () -> Unit,
    onClickCarrito: () -> Unit,
    onClickMisPedidos: () -> Unit,
    onClickCuenta: () -> Unit,
    cantidadLineas: Int
) {

    NavigationBar(containerColor = Color.Transparent) {
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 0, onClick = { onClickInicio() }, icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Inicio"
            )
        }, label = { Text(text = "Inicio") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 1, onClick = { onClickOfertas() }, icon = {
            Icon(
                imageVector = Icons.Default.Percent,
                contentDescription = "Ofertas"
            )
        }, label = { Text(text = "Ofertas") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 2, onClick = { onClickCarrito() }, icon = {

            BadgedBox(
                badge = {
                    if (cantidadLineas > 0)
                        Badge { Text(text = cantidadLineas.toString()) }
                }, modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "shoppingCart"
                )
            }

        }, label = { Text(text = "Compra") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 3, onClick = { onClickMisPedidos() }, icon = {
            Icon(
                imageVector = Icons.Default.Wysiwyg,
                contentDescription = "Mis Pedidos"
            )
        }, label = { Text(text = "Pedidos") })
        NavigationBarItem(colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.inverseOnSurface
        ), selected = index == 4, onClick = { onClickCuenta() }, icon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Cuenta"
            )
        }, label = { Text(text = "Cuenta") })
    }
}


@Composable
fun Queso() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        Image(
            painter = painterResource(id = R.drawable.queso),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingTopBasedOnApiSDK()),
            alpha = 0.7f
        )
    }
}

@Composable
fun Pizza() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    if (screenHeight > 620.dp) {

        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
                .alpha(0.4f), contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondopizza),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun CloseButton(onClickBack: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(paddingTopBasedOnApiSDK()),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { onClickBack() }, modifier = Modifier.padding(top = 20.dp)) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
        }
    }
}

@Composable
fun paddingTopBasedOnApiSDK(): PaddingValues {

    val sdk = Build.VERSION.SDK_INT

    return when {
        sdk > Build.VERSION_CODES.R -> PaddingValues(0.dp)
        else -> PaddingValues(top = 10.dp)
    }
}


@Composable
fun DividerMain() {
    HorizontalDivider(
        Modifier.padding(vertical = 6.dp),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.primary
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(title: String, onClickBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Home")
            }
        }
    )
}

