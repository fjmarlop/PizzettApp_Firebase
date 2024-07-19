package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Rule
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.AddressesClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.DetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.ExtendedDetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.HelpClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.HistoryClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.LoginScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.OffersClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.ShoppingCartClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.DividerMain
import es.fjmarlop.pizzettappfirebase.core.ui.theme.MainScaffoldView

@Composable
fun DetailClientScreen(navHost: NavHostController, viewModel: DetailClientViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val showPrivacy = rememberSaveable { mutableStateOf(false) }
    val showTerms = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.getClient()
    }

    MainScaffoldView(
        index = 4,
        onClickCuenta = { navHost.navigate(DetailClientScreenNav) },
        onClickOfertas = { navHost.navigate(OffersClientScreenNav) },
        onClickCarrito = { navHost.navigate(ShoppingCartClientScreenNav) },
        onClickMisPedidos = { navHost.navigate(HistoryClientScreenNav) },
        onClickInicio = { navHost.navigate(MainClientScreenNav) },
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState, enabled = true)
                .padding(
                    dimensionResource(id = R.dimen.padding_large)
                )
        ) {

            if (uiState.isAnonymous) {

                DetailClientHeaderAnonymous {
                    viewModel.logOut {
                        navHost.navigate(LoginScreenNav) { popUpTo<LoginScreenNav>() }
                    }
                }

            } else {

                DetailClientHeader(
                    uiState = uiState,
                    onClickDetails = { navHost.navigate(ExtendedDetailClientScreenNav) },
                    onClickAddress = { navHost.navigate(AddressesClientScreenNav(uiState.client!!.idClient)) },
                    onClickSettings = { }
                )

                HelpTermsPrivacy(
                    onClickHelp = { navHost.navigate(HelpClientScreenNav) },
                    onClickTerms = { showTerms.value = true },
                    onClickPrivacy = { showPrivacy.value = true }
                )

                DividerMain()
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

                DetailCloseSessionButton(content = stringResource(id = R.string.close)) {
                    viewModel.logOut {
                        navHost.popBackStack()
                        navHost.navigate(LoginScreenNav)
                    }
                }

                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

                DetailDeleteAccountButton(content = stringResource(id = R.string.deleteAccount)) {
                    uiState.client?.let {
                        viewModel.deleteAccount(it.idClient) {
                            navHost.popBackStack()
                            navHost.navigate(LoginScreenNav)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
        }

        if (showPrivacy.value) {
            AnimatedVisibility(visible = true) {
                PrivacyPolices { showPrivacy.value = false }
            }
        }
        if (showTerms.value) {
            AnimatedVisibility(visible = true) {
                TermsOfUses { showTerms.value = false }
            }
        }
    }

}


@Composable
fun DetailLoggedAccount(
    onClickDetails: () -> Unit,
    onClickAddress: () -> Unit,
    onClickSettings: () -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        DetailLoggedAccountItem(
            icon = Icons.Filled.Person,
            text = stringResource(id = R.string.detail)
        ) { onClickDetails() }
        DetailLoggedAccountItem(
            icon = Icons.AutoMirrored.Filled.ViewList,
            text = stringResource(id = R.string.adresses)
        ) { onClickAddress() }
        DetailLoggedAccountItem(
            icon = Icons.Filled.Settings,
            text = stringResource(id = R.string.settings)
        ) { onClickSettings() }
    }
}

@Composable
fun DetailLoggedAccountItem(icon: ImageVector, text: String, onClickButton: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClickButton() }) {
        Image(
            imageVector = icon,
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier.size(30.dp)
        )
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}


@Composable
fun PizzaPoints(points: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pizzapoints),
                    contentDescription = "pizza points",
                    modifier = Modifier.size(40.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(id = R.string.points))
                    Text(
                        text = "$points Pizzetta Points",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

    }
}


@Composable
fun DetailClientHeader(
    uiState: DetailClientUiState,
    onClickDetails: () -> Unit,
    onClickAddress: () -> Unit,
    onClickSettings: () -> Unit
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        uiState.client?.let {
            ProfileImage(imageUrl = it.imageClient)
            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
            ProfileName(name = it.nameClient)
            ProfileEmail(email = it.emailClient)
        }
    }
    if ((uiState.client?.pointsClient
            ?: 0) > 0
    ) uiState.client?.let { PizzaPoints(points = it.pointsClient) }
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
    DetailLoggedAccount(
        onClickDetails = { onClickDetails() },
        onClickAddress = { onClickAddress() },
        onClickSettings = { onClickSettings() }
    )
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
}

@Composable
fun ProfileEmail(email: String) {
    Text(
        text = email,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal =
                dimensionResource(id = R.dimen.padding_large)
            ),
        minLines = 2,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        lineHeight = 30.sp,
        softWrap = true,
    )
}

@Composable
fun ProfileName(name: String) {
    Text(
        text = name,
        fontSize = 22.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal =
                dimensionResource(id = R.dimen.padding_large)
            ),
        minLines = 2,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        lineHeight = 30.sp,
        softWrap = true,
    )
}

@Composable
fun ProfileImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clip(CircleShape)
            .size(110.dp), contentAlignment = Alignment.Center
    ) {
        if (imageUrl != "") {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(id = R.string.photo),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = stringResource(id = R.string.photo),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun DetailClientHeaderAnonymous(onClickButton: () -> Unit) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = stringResource(id = R.string.photo),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = R.string.noSession),
            fontSize = 22.sp,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
        Text(
            text = stringResource(id = R.string.benefits),
            fontSize = 18.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { onClickButton() },
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small))
                .width(250.dp)
        ) {
            Text(text = stringResource(id = R.string.initSession), fontSize = 20.sp)
        }
    }
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
}

@Composable
fun HelpTermsPrivacy(
    onClickPrivacy: () -> Unit,
    onClickTerms: () -> Unit,
    onClickHelp: () -> Unit
) {
    DividerMain()
    Column(Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickPrivacy() },
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.PrivacyTip,
                        contentDescription = "Privacy Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.privacy),
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickTerms() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Rule,
                        contentDescription = "Rules Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.terms),
                        modifier = Modifier.padding(start = 12.dp), fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickHelp() },
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "Help Icon", tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.help),
                        modifier = Modifier.padding(start = 12.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
    }
}

@Composable
fun DetailCloseSessionButton(content: String, onClickButton: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickButton() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = content,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = content,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun DetailDeleteAccountButton(content: String, onClickButton: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickButton() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = content,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = content,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 18.sp
                )
            }
        }
    }

}

@Composable
fun PrivacyPolices(onClickCloseButton: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            CloseButton { onClickCloseButton() }
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                        webChromeClient = WebChromeClient()
                        loadDataWithBaseURL(
                            null,
                            context.getString(R.string.html_privacy),
                            "text/html",
                            "utf-8",
                            null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }

    }
}

@Composable
fun TermsOfUses(onClickCloseButton: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {

            CloseButton { onClickCloseButton() }
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                        webChromeClient = WebChromeClient()
                        loadDataWithBaseURL(
                            null,
                            context.getString(R.string.html_termsOfUse),
                            "text/html",
                            "utf-8",
                            null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
