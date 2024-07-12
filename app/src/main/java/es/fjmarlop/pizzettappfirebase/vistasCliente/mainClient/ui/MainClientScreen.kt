package es.fjmarlop.pizzettappfirebase.vistasCliente.mainClient.ui

import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.DetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.HistoryClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.OffersClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.ShoppingCartClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.DividerMain
import es.fjmarlop.pizzettappfirebase.core.ui.theme.MainScaffoldView
import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel

@Composable
fun MainClientScreen(navHost: NavHostController, viewModel: MainClientViewModel) {

    val uiState by viewModel.uiState.collectAsState()


    LaunchedEffect(true) {
        viewModel.paintMainScreen()
    }

    MainScaffoldView(
        index = 0,
        onClickCuenta = { navHost.navigate(DetailClientScreenNav) },
        onClickOfertas = { navHost.navigate(OffersClientScreenNav) },
        onClickCarrito = { navHost.navigate(ShoppingCartClientScreenNav) },
        onClickMisPedidos = { navHost.navigate(HistoryClientScreenNav) },
        onClickInicio = { navHost.navigate(MainClientScreenNav) },
    )
    {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {

            uiState.client?.let { client ->

                val name =
                    if (client.aliasClient == "" && client.nameClient == "") client.emailClient.split(
                        "@"
                    ).first()
                    else if (client.aliasClient == "") client.nameClient.split(" ").first()
                    else client.aliasClient

                Welcome(nombre = name)
            }

            Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
            Title(string = R.string.mainTitle)
            DividerMain()
            Carousel(list = uiState.categories)
            DividerMain()
            Recommendations()
        }
    }

}

@Composable
fun Recommendations() {
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
    Title(string = R.string.recomendations)
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
    Row(Modifier.fillMaxWidth()) {
        RecommendationsItem(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
        RecommendationsItem(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
    }
    Row(Modifier.fillMaxWidth()) {
        RecommendationsItem(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
        RecommendationsItem(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
    }

}

@Composable
fun RecommendationsItem(modifier: Modifier = Modifier){
    val img = rememberAsyncImagePainter("https://i.postimg.cc/t4qD2VmR/carbonara.jpg")
    Card(modifier = modifier.padding(top = 12.dp), shape = RoundedCornerShape(15.dp)) {
        Box {
            Image(painter = img, contentDescription = null)
            Text(text = "Spaghetti Carbonara", color = Color.White, modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp), style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun Carousel(list: List<CategoryModel>) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, enabled = true)
    ) {
        list.forEach {
            CarouselItem(item = it, onClickItem = { /*TODO*/ })
        }
    }
}

@Composable
fun CarouselItem(item: CategoryModel, onClickItem: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        ImageCarousel(item = item, onClickItem = { onClickItem() })
        //Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.minimal_space)))
        Text(
            text = item.nameCategory,
            modifier = Modifier.padding(horizontal = 12.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun ImageCarousel(item: CategoryModel, onClickItem: () -> Unit) {

    val img = rememberAsyncImagePainter(item.urlImageCategory)

    var smallSize by rememberSaveable {
        mutableStateOf(true)
    }

    val size by animateDpAsState(
        targetValue = if (smallSize) 80.dp else 100.dp,
        animationSpec = tween(durationMillis = 500),
        finishedListener = { onClickItem() }, label = ""
    )

    Card(
        modifier = Modifier
            .size(size)
            //.clip(RoundedCornerShape(25.dp))
            .clip(CircleShape)
            .clickable {
                smallSize = !smallSize
            },
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = img,
                contentDescription = item.nameCategory,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun Welcome(nombre: String) {
    Text(
        text = "Hola, $nombre.",
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun Title(@StringRes string: Int) {
    Text(
        text = stringResource(string),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

