package es.fjmarlop.pizzettappfirebase.vistasGestion.productosGestion.newProducto.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.ui.theme.BackTopBar
import es.fjmarlop.pizzettappfirebase.entidades.model.TamanioModel

@Composable
fun NewProductoScreen(navHost: NavHostController, viewModel: NewProductoViewModel) {

    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    var catShow by remember {
        mutableStateOf(false)
    }
    var tamShow by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.resetUiState()
        viewModel.getCategoriesList()
    }

    Scaffold(topBar = { BackTopBar(title = stringResource(id = R.string.newProduct)) { navHost.navigateUp() } }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            Column {

                /** NOMBRE DEL PRODUCTO **/
                NewProductText(
                    name = uiState.nombreProducto,
                    title = stringResource(id = R.string.name)
                ) { viewModel.setName(it) }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

                /** DESCRIPCION DEL PRODUCTO **/
                NewProductText(
                    name = uiState.descriptionProduct,
                    title = stringResource(id = R.string.description),
                    altura = 150
                ) {
                    viewModel.setDescription(it)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

                /** URL DE IMAGEN DEL PRODUCTO **/
                NewProductText(
                    name = uiState.urlImgProducto,
                    title = stringResource(id = R.string.urlImg)
                ) {
                    viewModel.setUrlImg(it)
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))

                /** CATEGORIA DEL PRODUCTO **/
                NewCategoryText(uiState.category) { catShow = true }

                if (catShow) {
                    CatDialog(uiState.categoriesList,
                        onClickAceptar = {
                            viewModel.setCategory(it)
                            catShow = false
                        }) {
                        catShow = false
                    }
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))

                /** TAMAÑOS Y PVP DEL PRODUCTO **/
                TamanoPvp(tamPvpList = uiState.tamaniosPvpList) { tamShow = true }

                if (tamShow) {
                    TamanioDialog(){ tamShow = false }
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))

                /** BOTON CREAR PRODUCTO **/
                NewProductoSave { viewModel.createProduct("Error, no se ha podido crear el producto.") { navHost.navigateUp() } }
            }
        }
    }
}

@Composable
fun TamanioDialog(onClickDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onClickDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ){
            Text(
                text = "Añadir nuevos tamaños-pvp",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun TamanoPvp(tamPvpList: List<TamanioModel>, onClick: () -> Unit) {

    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))) {
        Text(
            text = stringResource(id = R.string.sizePvp),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.minimal_space))
        )

        if (tamPvpList.isEmpty()) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                enabled = false,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.addTamanoPvp),
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
                )
            )
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                tamPvpList.forEach { item ->
                    Text(text = "tamaño ${item.nameTamanio} - pvp: ${item.priceTamanio} €")
                }
            }
        }
    }
}


@Composable
fun CatDialog(list: List<String>, onClickAceptar: (String) -> Unit, onClickDismiss: () -> Unit) {

    val selectedValue = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onClickDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column {
                Text(
                    text = "Seleccione una categoría",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                LazyColumn {
                    items(list) { item ->
                        OptionRadio(item, selectedValue.value) { select ->
                            selectedValue.value = select
                        }
                    }
                }

                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = dimensionResource(id = R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { onClickDismiss() }) {
                        Text(text = "Cancelar")
                    }
                    TextButton(onClick = { onClickAceptar(selectedValue.value) }) {
                        Text(text = "Aceptar")
                    }
                }

            }
        }
    }
}

@Composable
fun OptionRadio(item: String, selectedValue: String, onClick: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = (item == selectedValue), onClick = { onClick(item) })
    ) {
        RadioButton(selected = (item == selectedValue), onClick = { onClick(item) })
        Text(text = item)
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
fun NewCategoryText(value: String = "", onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    ) {
        Text(
            text = stringResource(id = R.string.titleCategories),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.minimal_space))
        )
        OutlinedTextField(
            value = value,
            enabled = false,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { }),
            placeholder = { Text(text = "Seleccione una categoria") },
            maxLines = 1,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface
            )
        )
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
            placeholder = { Text(text = "$title del producto") },
            maxLines = 1
        )
    }
}

@Composable
fun NewProductText(name: String, title: String, altura: Int, nameChanged: (String) -> Unit) {
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
            modifier = Modifier
                .fillMaxWidth()
                .height(altura.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { }),
            placeholder = { Text(text = "$title del producto") }
        )
    }
}



