package com.fatihbicgi.ecommerceapp.scenes.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fatihbicgi.ecommerceapp.uikit.ECommerceTexField

@Composable
fun LoginScreen(
    uiState: LoginContract.UiState,
    onAction: (LoginContract.UiAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        ECommerceTexField(
            title = "name",
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
}