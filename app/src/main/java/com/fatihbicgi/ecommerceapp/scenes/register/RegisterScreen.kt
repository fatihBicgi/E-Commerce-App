package com.fatihbicgi.ecommerceapp.scenes.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.Action
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatihbicgi.ecommerceapp.scenes.login.LoginContract
import com.fatihbicgi.ecommerceapp.uikit.ECommerceTexField

@Composable
fun RegisterScreen(
    uiState: RegisterContract.UiState,
    onAction: (RegisterContract.UiAction) -> Unit, // fonksiyonu değişken olarak tanımlıyoruz. ve fonksiyonu taşıyabiliriyoruz
    // high order funcs
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
            title = "email",
            value = uiState.email,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnEmailChange(it)) },
            leadingIcon = Icons.Filled.AccountCircle
        )
        ECommerceTexField(
            title = "password",
            value = uiState.password,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnPasswordChange(it)) },
            leadingIcon = Icons.Filled.Build,
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { onAction.invoke(RegisterContract.UiAction.OnPasswordVisibilityChange) }) {
                    Text(text = if (uiState.isPasswordVisible) "Hide" else "Show")
                }
            })
        ECommerceTexField(
            title = "name",
            value = uiState.name,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnNameChange(it)) },
            leadingIcon = Icons.Filled.Edit
        )
        ECommerceTexField(
            title = "phone",
            value = uiState.phone,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnPhoneChange(it)) },
            leadingIcon = Icons.Filled.Phone
        )
        ECommerceTexField(
            title = "address",
            value = uiState.address,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnAddressChange(it)) },
            leadingIcon = Icons.Filled.Home
        )
        Button(
            onClick = {
                onAction.invoke(RegisterContract.UiAction.OnRegisterClick)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
        )
        {
            Text(text = "Register")
        }
    }

}

