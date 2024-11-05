package com.fatihbicgi.ecommerceapp.scenes.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fatihbicgi.ecommerceapp.uikit.ECommerceTexField

@Composable
fun LoginScreen(
    uiState: LoginContract.UiState,
    onAction: (LoginContract.UiAction) -> Unit,
    onGoToUserDetailScreen: () -> Unit,
) {
    if (uiState.isLoginSuccessfuly) onGoToUserDetailScreen.invoke()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        val viewModel = hiltViewModel<LoginViewModel>()
        val context = LocalContext.current
        // Toast mesajını tek seferlik dinlemek için
        LaunchedEffect(Unit) {
            viewModel.validationMessages.collect { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        // Hata mesajlarını göster
        /*uiState.validationErrors.forEach { error ->
            //toast sürekli tekrar tekrar yazdırıyor.
            Text(
                text = error,
                fontSize = 14.sp,
                color = Color.Red, // Hata mesajları kırmızı renkte
            )
        }*/
        ECommerceTexField(
            title = "email",
            value = uiState.email,
            onTextChange = { onAction.invoke(LoginContract.UiAction.OnEmailChange(it)) },
            leadingIcon = Icons.Filled.Email,
        )
        ECommerceTexField(
            title = "password",
            value = uiState.password,
            onTextChange = { onAction.invoke(LoginContract.UiAction.OnPasswordChange(it)) },
            leadingIcon = Icons.Filled.Build,
            trailingIcon = {
                TextButton(onClick = { onAction.invoke(LoginContract.UiAction.OnPasswordVisibilityChange) }) {
                    Text(text = if (uiState.isPasswordVisible) "Hide" else "Show")
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Remember Me"
            )
            Checkbox(
                checked = uiState.rememberMe,
                onCheckedChange = { onAction.invoke(LoginContract.UiAction.OnRememberMeChange) },
                enabled = true,
                colors = CheckboxDefaults.colors(Color.Red),
            )
            Button(
                onClick = {
                    onAction.invoke(LoginContract.UiAction.OnLoginClick)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
            )
            {
                Text(text = "Login")
            }
        }
        Log.i("uiState.rememberMe", uiState.rememberMe.toString())
    }
}