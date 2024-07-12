package es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.extendedDetailClient

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.vistasCliente.detailsClient.ui.mainDetailClient.ProfileImage
import es.fjmarlop.pizzettappfirebase.core.navigation.DetailClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun ExtendedDetailClientScreen(
    navHost: NavHostController,
    viewModel: ExtendedDetailClientViewModel
) {

    LaunchedEffect(true) {
        viewModel.getClient()
    }

    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val updateImage = stringResource(id = R.string.updateImage)
    val updateAlias = stringResource(id = R.string.updateAlias)
    val updateName = stringResource(id = R.string.updateName)
    val updatePhone = stringResource(id = R.string.updatePhone)

    Queso()
    Pizza()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState, enabled = true)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CloseButton {
                navHost.popBackStack()
                navHost.navigate(DetailClientScreenNav)
            }

            uiState.client?.let { client ->

                ImageToBeEdit(
                    imageUrl = client.imageClient,
                    newImageUrl = uiState.newImageUrl,
                    onTextChange = { viewModel.setTextImageClient(it) },
                    onClickSave = {
                        viewModel.updateImageUrlClient(
                            uiState.newImageUrl,
                            client.idClient,
                            updateImage
                        )
                    },
                    resetValue = { viewModel.resetNewImageUrlValue() }
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))

                TextToBeEdit(
                    description = stringResource(id = R.string.alias),
                    descriptionEdit = "Alias",
                    value = client.aliasClient,
                    newValue = uiState.newAlias,
                    onTextChange = { viewModel.setAliasClient(it) },
                    onClickSave = {
                        viewModel.updateAliasClient(
                            uiState.newAlias,
                            client.idClient,
                            updateAlias
                        )
                    },
                    resetValue = { viewModel.resetNewAliasValue() }
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                TextToBeEdit(
                    description = stringResource(id = R.string.name),
                    descriptionEdit = stringResource(id = R.string.name),
                    value = client.nameClient,
                    newValue = uiState.newName,
                    onTextChange = { viewModel.setNameClient(it) },
                    onClickSave = {
                        viewModel.updateNameClient(
                            uiState.newName, client.idClient,
                            updateName
                        )
                    },
                    resetValue = { viewModel.resetNewNameValue() }
                )
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                TextToBeEdit(
                    description = stringResource(id = R.string.phone),
                    descriptionEdit = stringResource(id = R.string.phone),
                    value = client.phoneClient,
                    newValue = uiState.newPhone,
                    onTextChange = { viewModel.setPhoneClient(it) },
                    onClickSave = {
                        viewModel.updatePhoneClient(
                            uiState.newPhone,
                            client.idClient,
                            updatePhone
                        )
                    },
                    resetValue = { viewModel.resetNewPhoneValue() }
                )
            }
        }
    }
}

@Composable
fun TextToBeEdit(
    description: String,
    descriptionEdit: String,
    value: String,
    newValue: String,
    onTextChange: (String) -> Unit,
    onClickSave: () -> Unit,
    resetValue: () -> Unit
) {

    var isEdit by rememberSaveable { mutableStateOf(false) }

    if (isEdit) {

        var canSave by rememberSaveable { mutableStateOf(false) }

        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            value = newValue,
            onValueChange = {
                canSave = true
                onTextChange(it)
            },
            label = { Text(text = descriptionEdit) },
            trailingIcon = {
                Row {

                    IconButton(onClick = {
                        isEdit = !isEdit
                        resetValue()
                    }) {
                        Icon(imageVector = Icons.Default.Cancel, contentDescription = "cancel")
                    }
                    if (canSave) {
                        IconButton(onClick = {
                            onClickSave()
                            isEdit = !isEdit
                        }) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = "save")
                        }
                    }
                }
            })
    } else {
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = description) },
            value = value,
            onValueChange = {},
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { isEdit = !isEdit }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "edit")
                }
            }
        )
    }
}

@Composable
fun ImageToBeEdit(
    imageUrl: String,
    newImageUrl: String,
    onTextChange: (String) -> Unit,
    onClickSave: () -> Unit,
    resetValue: () -> Unit
) {

    var isEdit by rememberSaveable { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ProfileImage(imageUrl = imageUrl)
        Text(
            text = stringResource(id = R.string.editRoute),
            modifier = Modifier.clickable { isEdit = !isEdit },
            fontSize = 12.sp
        )
    }

    if (isEdit) {

        var canSave by rememberSaveable { mutableStateOf(false) }

        Column {
            Text(
                text = imageUrl,
                fontSize = 10.sp,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = newImageUrl,
                onValueChange = {
                    canSave = true
                    onTextChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(id = R.string.urlImage)) },
                trailingIcon = {
                    Row {
                        IconButton(onClick = {
                            isEdit = !isEdit
                            resetValue()
                        }) {
                            Icon(imageVector = Icons.Default.Cancel, contentDescription = "cancel")
                        }
                        if (canSave) {
                            IconButton(onClick = { onClickSave() }) {
                                Icon(imageVector = Icons.Default.Save, contentDescription = "save")
                            }
                        }
                    }
                },
                singleLine = true
            )
        }
    }
}
