package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.createAcount

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.LoginScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun CreateAccountScreen(navHost: NavHostController, viewModel: CreateAccountViewModel) {

    val uiState: CreateAccountUiState by viewModel.uiState.collectAsState()

    val focusRequesterEmail = remember { FocusRequester() }
    val focusRequesterPass = remember { FocusRequester() }
    val focusRequesterReplyPass = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.resetUiState()
    }

    Queso()
    Pizza()
    CloseButton { navHost.navigateUp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large))
            .verticalScroll(scrollState, enabled = true),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleCAS()
            Spacer(Modifier.size(16.dp))
            UserCreate(
                user = uiState.email,
                isValid = uiState.isEmailValid,
                onTextChanged = { viewModel.setEmailText(it) },
                focusRequester = focusRequesterEmail,
                onClickImeActionDone = { focusRequesterPass.requestFocus() })
            Spacer(modifier = Modifier.size(8.dp))
            PasswordCreate(
                password = uiState.password,
                isValid = uiState.isPasswordValid,
                onTextChanged = { viewModel.setPasswordText(it) },
                focusRequester = focusRequesterPass,
                onClickImeActionDone = { focusRequesterReplyPass.requestFocus() })
            ReplyPasswordCreate(
                password = uiState.confirmPassword,
                onTextChanged = { viewModel.setReplyPasswordText(it) },
                isValid = uiState.isConfirmPasswordValid,
                focusRequester = focusRequesterReplyPass,
                onClickImeActionDone = { keyboardController?.hide() })
            CreateButton(
                isEnable = uiState.isFormValid,
                onClickRegister = { viewModel.createAccount { navHost.navigate(LoginScreenNav) } }
            )
        }

    }

}


@Composable
fun TitleCAS() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.logo_la_pizzetta),
            contentDescription = "Logo"
        )
        Text(
            text = stringResource(id = R.string.createAcc),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
}


@Composable
fun CreateButton(
    isEnable: Boolean,
    onClickRegister: () -> Unit
) {
    Button(
        onClick = { onClickRegister() },
        enabled = isEnable,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(text = stringResource(id = R.string.createAcc))
    }
}

@Composable
fun UserCreate(
    user: String, isValid: Boolean,
    onTextChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    onClickImeActionDone: () -> Unit
) {

    OutlinedTextField(
        value = user,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = { Text(text = stringResource(id = R.string.email)) },
        placeholder = { Text(text = stringResource(id = R.string.email2)) },
        maxLines = 1,
        singleLine = true,
        isError = !isValid,
        supportingText = {
            if (!isValid) Text(text = stringResource(id = R.string.emailError))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
        keyboardActions = KeyboardActions(onDone = { onClickImeActionDone() })

    )

}

@Composable
fun PasswordCreate(
    password: String,
    onTextChanged: (String) -> Unit,
    isValid: Boolean,
    focusRequester: FocusRequester,
    onClickImeActionDone: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = { Text(text = stringResource(id = R.string.pass)) },
        placeholder = { Text(text = stringResource(id = R.string.pass2)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
        singleLine = true,
        maxLines = 1,
        isError = !isValid,
        supportingText = {
            if (!isValid) Text(text = stringResource(id = R.string.passValid))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardActions = KeyboardActions(onDone = { onClickImeActionDone() })
    )
}

@Composable
fun ReplyPasswordCreate(
    password: String,
    onTextChanged: (String) -> Unit,
    isValid: Boolean,
    focusRequester: FocusRequester,
    onClickImeActionDone: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        label = { Text(text = stringResource(id = R.string.pass)) },
        placeholder = { Text(stringResource(id = R.string.passReply)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
            cursorColor = MaterialTheme.colorScheme.tertiary,
            focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        ),
        singleLine = true,
        maxLines = 1,
        isError = !isValid,
        supportingText = {
            if (!isValid) Text(text = stringResource(id = R.string.passReply2))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Filled.VisibilityOff
            } else {
                Icons.Filled.Visibility
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "show password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardActions = KeyboardActions(onDone = { onClickImeActionDone() })
    )
}