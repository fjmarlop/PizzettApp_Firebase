package es.fjmarlop.pizzettappfirebase.vistasGestion.categoriasGestion.mainCategorias.ui

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.ui.theme.BackTopBar
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.entidades.model.CategoryModel

@Composable
fun MainCategoriasScreen(navHost: NavHostController, viewModel: MainCategoriaViewmodel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle(MainCategoriasUiState())

    Scaffold(topBar = {
        BackTopBar(title = stringResource(id = R.string.titleCategories)) { navHost.navigateUp() }
    }, floatingActionButton = { NewCategoryFab { /* TODO */ } }) { paddingValues ->
        Pizza()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.categorias.isEmpty()) {
                ErrorMsg("No existen categorias registradas")
            } else {
                CategoriasList(uiState.categorias)
            }
        }
    }
}

@Composable
fun ErrorMsg(msg: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(text = msg)
    }
}

@Composable
fun CategoriasList(categorias: List<CategoryModel>) {
    LazyColumn {
        items(categorias) { category -> CategoriasItem(categoria = category){} }
    }
}

@Composable
fun CategoriasItem(categoria: CategoryModel, onClick: () -> Unit) {
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
                text = categoria.nameCategory.take(1).uppercase(),
                modifier = Modifier,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Text(
            text =  categoria.nameCategory, fontSize = 16.sp, modifier = Modifier
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

@Composable
fun NewCategoryFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.newCategory)) },
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.newCategory)
            )
        },
        onClick = { onClick() },
        expanded = true,
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        modifier = Modifier.padding(16.dp)
    )
}