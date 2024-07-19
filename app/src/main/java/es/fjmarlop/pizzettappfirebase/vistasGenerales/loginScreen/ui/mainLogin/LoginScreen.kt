package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.mainLogin

import android.app.Activity
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.navigation.MainClientScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.MainManagementScreenNav
import es.fjmarlop.pizzettappfirebase.core.navigation.SingInMailScreenNav
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso

@Composable
fun LoginScreen(navHost: NavHostController, viewModel: LoginViewModel) {

    val scrollState = rememberScrollState()

    val activity = LocalContext.current as Activity

    val googleLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.loginWithGoogle(account.idToken!!) {
                        navHost.navigate(MainClientScreenNav)
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }

        }

    Scaffold { padding ->
        Queso()
        Pizza()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState, enabled = true)
                    .padding(
                        dimensionResource(id = R.dimen.padding_large)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Logo()
                Spacer(modifier = Modifier.size(48.dp))

                /** MODO DEMO PARA PODER USAR LA APP SIN REGISTRO, SE HARA SESIÓN CON DOS CUENTAS POR DEFECTO. **/
                DemoMode(
                    onClickClient = {
                        viewModel.loginDemoMode(1) {
                            navHost.navigate(
                                MainClientScreenNav
                            )
                        }
                    },
                    onClickManagement = {
                        viewModel.loginDemoMode(2) {
                            navHost.navigate(
                                MainManagementScreenNav
                            )
                        }
                    }
                )
                /** FIN MODO DEMO **/

                ButtonSessionEmail { navHost.navigate(SingInMailScreenNav) }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
                ButtonSessionGoogle {
                    viewModel.inGoogleLoginSelected(
                        googleLauncherLogin = { googleLauncher.launch(it.signInIntent) })
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))
                ButtonSessionTwitter {
                    viewModel.loginWithTwitter(activity) {
                        navHost.navigate(
                            MainClientScreenNav
                        )
                    }
                }
                Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
                Text(
                    text = stringResource(id = R.string.invitado),
                    modifier = Modifier.clickable {
                        viewModel.anonymousLogin {
                            navHost.navigate(
                                MainClientScreenNav
                            )
                        }
                    },
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DemoMode(onClickClient: () -> Unit, onClickManagement: () -> Unit) {

    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = "MODO DEMO",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.clickable { showDialog = true })

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp), contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Modo demostración.", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "Este modo te permite usar la aplicación como un usuario registrado sin necesidad de iniciar sesión o crear una cuenta.",
                        textAlign = TextAlign.Justify, fontSize = 13.sp
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        TextButton(onClick = {
                            onClickClient()
                            showDialog = false
                        }) { Text(text = "Modo Cliente") }
                        TextButton(onClick = {
                            onClickManagement()
                            showDialog = false
                        }) {
                            Text(
                                text = "Modo Gestión"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.size(6.dp))
                }
            }
        }
    }
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_large)))
}

@Composable
fun Logo() {

    Image(
        painter = painterResource(id = R.drawable.logo_la_pizzetta),
        contentDescription = "logo",
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
    )
    Text(
        text = stringResource(id = R.string.bienve),
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_medium)))
    TextLogin(text = R.string.login_text_1, fontWeight = FontWeight.Normal)
    TextLogin(text = R.string.login_text_2, fontWeight = FontWeight.Normal)
    TextLogin(text = R.string.login_text_3, fontWeight = FontWeight.SemiBold)
}

@Composable
fun TextLogin(@StringRes text: Int, fontWeight: FontWeight) {
    Text(
        text = stringResource(id = text),
        fontSize = 14.sp, textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = fontWeight,
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.minimal_space)))
}

@Composable
fun ButtonSessionEmail(onSignInMailClick: () -> Unit) {
    OutlinedButton(
        onClick = { onSignInMailClick() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Filled.Mail,
                contentDescription = "logo Email",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_large))
            )

            Text(
                text = stringResource(id = R.string.login_mail),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ButtonSessionGoogle(onSignInGoogleClick: () -> Unit) {
    OutlinedButton(
        onClick = { onSignInGoogleClick() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "logo Google",
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    .size(24.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = stringResource(id = R.string.login_google),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ButtonSessionTwitter(onSignInTwitterClick: () -> Unit) {
    OutlinedButton(
        onClick = { onSignInTwitterClick() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.twitter_x),
                contentDescription = "logo Twitter",
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                    .size(24.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                text = stringResource(id = R.string.login_X),
                fontSize = 16.sp,
            )
        }
    }
}