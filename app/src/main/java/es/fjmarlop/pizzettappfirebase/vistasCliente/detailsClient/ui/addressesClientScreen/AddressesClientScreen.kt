package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.addressesClientScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HouseSiding
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.AddressesClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.DetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso
import es.fjmarlop.pizzettappfirebase.entidades.model.AddressModel

@Composable
fun AddressesClientScreen(
    navHost: NavHostController,
    viewModel: AddressesClientViewModel,
    idClient: String
) {

    LaunchedEffect(key1 = true) {
        viewModel.getAddressesClient(idClient)
    }

    val uiState by viewModel.uiState.collectAsState()

    var showAddAddress by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Queso()
    Pizza()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.TopStart
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CloseButton {
                navHost.popBackStack()
                navHost.navigate(DetailClientScreenNav)
            }
            Text(
                text = stringResource(id = R.string.note),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small)))
            HorizontalDivider()

            if (uiState.addressList.isEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            dimensionResource(id = R.dimen.padding_large)
                        ),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Image(
                        Icons.Filled.LocationOn,
                        contentDescription = "",
                        modifier = Modifier.padding(end = 12.dp)
                    )

                    Text(
                        text = stringResource(id = R.string.noAddress),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            } else {

                val msg = stringResource(id = R.string.deleteAddressFail)
                val mgsFav = stringResource(id = R.string.doFavFail)

                AddressClientList(
                    list = uiState.addressList,
                    onClickDelete = { viewModel.deleteAddress(it, idClient, msg) },
                    onClickFav = { viewModel.doFav(it,idClient, mgsFav) }
                )
            }

            Button(
                onClick = {
                    showAddAddress = true
                    viewModel.resetNewAddress()
                }, modifier = Modifier.padding(
                    top = dimensionResource(
                        id = R.dimen.padding_large
                    )
                )
            ) {
                Text(stringResource(id = R.string.addAddress), fontSize = 16.sp)
            }
        }
    }
    if (showAddAddress) {

        val msg = stringResource(id = R.string.newAddressFail)

        AddAddress(
            onDismissRequest = {
                showAddAddress = false
                navHost.navigate(AddressesClientScreenNav(idClient))
            },
            nameAddress = uiState.nameNewAddress,
            setNameAddress = { viewModel.setNameAddress(it) },
            streetAddress = uiState.streetNewAddress,
            setStreetAddress = { viewModel.setStreetAddress(it) },
            numberAddress = uiState.numberNewAddress,
            setNumberAddress = { viewModel.setNumberAddress(it) },
            stairsAddress = uiState.stairsNewAddress,
            setStairsAddress = { viewModel.setStairsAddress(it) },
            floorAddress = uiState.floorNewAddress,
            setFloorAddress = { viewModel.setFloorAddress(it) },
            doorAddress = uiState.doorNewAddress,
            setDoorAddress = { viewModel.setDoorAddress(it) },
            provinceAddress = uiState.provinceNewAddress,
            setProvinceAddress = { viewModel.setProvinceAddress(it) },
            cityAddress = uiState.cityNewAddress,
            setCityAddress = { viewModel.setCityAddress(it) },
            postalCodeAddress = uiState.postalCodeNewAddress,
            setPostalCodeAddress = { viewModel.setPostalCodeAddress(it) },
            onClickSave = {
                viewModel.saveNewAddress(
                    idClient = idClient,
                    uiState = uiState,
                    msg = msg
                ) { showAddAddress = false }
            },
            onSaveEnable = viewModel.enableSaveButton()
        )
    }
}


@Composable
fun AddressClientList(
    list: List<AddressModel>,
    onClickDelete: (id: String) -> Unit,
    onClickFav: (id: String) -> Unit
) {
    LazyColumn {
        items(list) { address ->
            AddressItem(
                addressModel = address,
                onClickDelete = { onClickDelete(it) },
                onClickFav = { onClickFav(it) }
            )
        }
    }
}

@Composable
fun AddressItem(
    addressModel: AddressModel,
    onClickDelete: (id: String) -> Unit,
    onClickFav: (id: String) -> Unit,
) {
    var delete by rememberSaveable { mutableStateOf(false) }

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                .pointerInput(Unit) {
                    detectTapGestures(onLongPress = {
                        delete = true
                    })
                }
                .weight(1f)
        ) {
            Text(text = addressModel.nameAddress, style = MaterialTheme.typography.titleLarge)
            Text(text = addressModel.streetAddress, style = MaterialTheme.typography.bodyMedium)
            Row {
                Text(
                    text = "${addressModel.postalCodeAddress}, ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${addressModel.cityAddress}, ",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = addressModel.provinceAddress,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        if (delete) {
            Row {
                IconButton(onClick = {
                    delete = false
                    onClickDelete(addressModel.idAddress)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                }
                IconButton(onClick = { delete = false }) {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "cancel",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            IconButton(onClick = { onClickFav(addressModel.idAddress) }) {
                if (addressModel.favAddress) Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Fav",
                    tint = MaterialTheme.colorScheme.primary
                ) else Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "noFav",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }
        }
    }
    HorizontalDivider()
}

@Composable
fun AddAddress(
    onDismissRequest: () -> Unit,
    nameAddress: String,
    setNameAddress: (String) -> Unit,
    streetAddress: String,
    setStreetAddress: (String) -> Unit,
    numberAddress: String,
    setNumberAddress: (String) -> Unit,
    stairsAddress: String,
    setStairsAddress: (String) -> Unit,
    floorAddress: String,
    setFloorAddress: (String) -> Unit,
    doorAddress: String,
    setDoorAddress: (String) -> Unit,
    provinceAddress: String,
    setProvinceAddress: (String) -> Unit,
    cityAddress: String,
    setCityAddress: (String) -> Unit,
    postalCodeAddress: String,
    setPostalCodeAddress: (String) -> Unit,
    onSaveEnable: Boolean,
    onClickSave: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Queso()
        Pizza()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .verticalScroll(scrollState, enabled = true)
        ) {

            CloseButton {
                onDismissRequest()
            }
            Text(
                text = stringResource(id = R.string.newAddress),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.addressData),
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            NewAddressItem(
                value = nameAddress,
                label = stringResource(id = R.string.name),
                icon = Icons.Filled.Home
            ) { setNameAddress(it) }

            NewAddressItem(
                value = streetAddress,
                label = stringResource(id = R.string.address),
                icon = Icons.Filled.LocationOn
            ) { setStreetAddress(it) }

            NewAddressItem(
                value = numberAddress,
                label = stringResource(id = R.string.number),
                icon = Icons.Filled.Numbers,
                number = true
            ) { setNumberAddress(it) }

            NewAddressItem(
                value = stairsAddress,
                label = stringResource(id = R.string.stairs),
                icon = Icons.Filled.Stairs,
            ) { setStairsAddress(it) }

            NewAddressItem(
                value = floorAddress,
                label = stringResource(id = R.string.floor),
                icon = Icons.Filled.HouseSiding,
                number = true
            ) { setFloorAddress(it) }

            NewAddressItem(
                value = doorAddress,
                label = stringResource(id = R.string.door),
                icon = Icons.Filled.DoorFront
            ) { setDoorAddress(it) }

            NewAddressItem(
                value = provinceAddress,
                label = stringResource(id = R.string.province),
                icon = Icons.Filled.LocationCity
            ) { setProvinceAddress(it) }

            NewAddressItem(
                value = cityAddress,
                label = stringResource(id = R.string.city),
                icon = Icons.Filled.LocationCity
            ) { setCityAddress(it) }

            NewAddressItem(
                value = postalCodeAddress,
                label = stringResource(id = R.string.postalCode),
                icon = Icons.Filled.PostAdd,
                number = true,
                lastItem = true
            ) { setPostalCodeAddress(it) }

            Button(onClick = { onClickSave() }, enabled = onSaveEnable) {
                Text(text = stringResource(id = R.string.addAddress), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.size(80.dp))
        }

    }
}


@Composable
fun NewAddressItem(
    value: String,
    label: String,
    icon: ImageVector,
    number: Boolean = false,
    lastItem: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Column() {
        Text(
            text = label,
            modifier = Modifier
                .padding(bottom = 10.dp, start = dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start
        )
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label, fontSize = 12.sp) },
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background
            ),
            leadingIcon = { Icon(icon, contentDescription = "") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (number) KeyboardType.Number else KeyboardType.Text,
                imeAction = if (lastItem) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions =
            KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Next) },
                onDone = { focusManager.clearFocus() }
            )
        )

    }
}



