package es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.recoveryPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import es.fjmarlop.pizzettappfirebase.R
import es.fjmarlop.pizzettappfirebase.core.ui.theme.CloseButton
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Pizza
import es.fjmarlop.pizzettappfirebase.core.ui.theme.Queso
import es.fjmarlop.pizzettappfirebase.vistasGenerales.loginScreen.ui.signInMail.UserMail

@Composable
fun RecoveryPasswordScreen(navHost: NavHostController, viewModel: RecoveryPasswordViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    val focusRequesterUser = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(true) {
        viewModel.resetUiState()
    }

    Queso()
    Pizza()
    CloseButton { navHost.navigateUp() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_la_pizzetta),
                contentDescription = "logo pizzetta"
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = "Restaurar contraseña",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.size(18.dp))
            UserMail(
                userMail = uiState.email,
                focusRequester = focusRequesterUser,
                onClickImeActionDone = {
                    keyboardController?.hide()
                },
                onTextChanged = { viewModel.setTextEmail(it) })
            Spacer(modifier = Modifier.size(18.dp))
            OutlinedButton(
                onClick = { viewModel.recoveryPassword(uiState.email) { navHost.navigateUp() } },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = uiState.isRecoveryButtonEnable
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Restore,
                        contentDescription = "Reset password"
                    )
                    Text(text = "Restaurar", modifier = Modifier.padding(horizontal = 8.dp))
                }
            }
            Spacer(modifier = Modifier.size(18.dp))
            if (uiState.isLoading) CircularProgressIndicator()
        }
    }
}