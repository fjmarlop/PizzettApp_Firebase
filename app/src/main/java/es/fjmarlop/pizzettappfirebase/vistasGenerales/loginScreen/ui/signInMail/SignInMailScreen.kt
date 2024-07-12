package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.CreateAccountScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainManagementScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.RecoveryPasswordScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun SignInMailScreen(navHost: NavHostController, viewModel: SignInMailViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    val focusRequesterUser = remember { FocusRequester() }

    LaunchedEffect(true) {
        viewModel.resetUiState()
    }

    Scaffold { paddingValues ->

        Queso()
        Pizza()
        CloseButton { navHost.navigateUp() }

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                SignInEmailHeader()
                SignInEmailBody(
                    email = uiState.email,
                    password = uiState.password,
                    isLoginEnable = uiState.isLoginEnable,
                    setEmailText = { viewModel.setEmailText(it, uiState.password) },
                    setPasswordText = { viewModel.setPasswordText(it, uiState.email) },
                    onClickSignInMail = {
                        viewModel.loginEmail(
                            focusEmail = { focusRequesterUser.requestFocus() },
                            navigateManagement = { navHost.navigate(MainManagementScreenNav) },
                            navigateClient = { navHost.navigate(MainClientScreenNav) }
                        )
                    },
                    onClickRegisterAccount = { navHost.navigate(CreateAccountScreenNav) },
                    onClickForgetPassword = { navHost.navigate(RecoveryPasswordScreenNav) },
                    isLoading = uiState.isLoading,
                    focusRequesterUser = focusRequesterUser
                )

            }

        }
    }

}

@Composable
fun SignInEmailHeader() {
    Image(
        painter = painterResource(id = R.drawable.logo_la_pizzetta),
        contentDescription = "logo",
        modifier = Modifier.padding(24.dp)
    )
    Text(
        text = stringResource(id = R.string.initSession),
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}

@Composable
fun SignInEmailBody(
    email: String,
    password: String,
    isLoginEnable: Boolean,
    setEmailText: (String) -> Unit,
    setPasswordText: (String) -> Unit,
    onClickSignInMail: () -> Unit,
    onClickRegisterAccount: () -> Unit,
    onClickForgetPassword: () -> Unit,
    isLoading: Boolean,
    focusRequesterUser: FocusRequester
) {

    // Crear dos FocusRequester para controlar el foco entre los TextField
    val focusRequesterPass = remember { FocusRequester() }

    Spacer(modifier = Modifier.size(16.dp))
    UserMail(
        userMail = email,
        focusRequester = focusRequesterUser,
        onClickImeActionDone = {
            focusRequesterPass.requestFocus()
        },
        onTextChanged = { setEmailText(it) })
    Spacer(modifier = Modifier.size(12.dp))
    Password(
        password = password,
        focusRequester = focusRequesterPass,
        onTextChanged = { setPasswordText(it) })
    Spacer(modifier = Modifier.size(12.dp))
    LoginButton(
        loginEnable = isLoginEnable,
        onClickSignInMail = { onClickSignInMail() },
    )
    Spacer(modifier = Modifier.size(12.dp))
    AccountRegister { onClickRegisterAccount() }
    Spacer(modifier = Modifier.size(8.dp))
    ForgetPassword { onClickForgetPassword() }
    Spacer(modifier = Modifier.size(16.dp))

    if (isLoading) {
        CircularProgressIndicator(Modifier.padding(32.dp))
    }
}

@Composable
fun UserMail(
    userMail: String,
    focusRequester: FocusRequester,
    onClickImeActionDone: () -> Unit,
    onTextChanged: (String) -> Unit
) {

    TextField(
        value = userMail,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .focusRequester(focusRequester),
        label = { Text(text = stringResource(id = R.string.email)) },
        placeholder = { Text(text = stringResource(id = R.string.email2)) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(onDone = { onClickImeActionDone() })
    )
}

@Composable
fun Password(password: String, focusRequester: FocusRequester, onTextChanged: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(false) }

    // Obtener el controlador de teclado
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            .clip(CircleShape)
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .focusRequester(focusRequester),
        label = { Text(text = stringResource(id = R.string.pass)) },
        placeholder = { Text(stringResource(id = R.string.pass2)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
        singleLine = true,
        maxLines = 1,
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
                Icon(
                    imageVector = image,
                    contentDescription = "show password"
                )
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        })
    )
}

@Composable
fun LoginButton(
    loginEnable: Boolean,
    onClickSignInMail: () -> Unit,
) {
    Button(
        onClick = {
            onClickSignInMail()
        },
        enabled = loginEnable,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
    ) {
        Text(text = stringResource(id = R.string.initSession))
    }

}

@Composable
fun ForgetPassword(onClickForgetPassword: () -> Unit) {
    Text(
        text = stringResource(id = R.string.forgetpass),
        modifier = Modifier.clickable { onClickForgetPassword() },
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}

@Composable
fun AccountRegister(onClickRegisterAccount: () -> Unit) {
    Row {
        Text(
            text = stringResource(id = R.string.newUser),
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = stringResource(id = R.string.createAcc),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onClickRegisterAccount() })
    }
}