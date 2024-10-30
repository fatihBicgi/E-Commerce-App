package com.fatihbicgi.ecommerceapp.scenes.register

import NanpVisualTransformation
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fatihbicgi.ecommerceapp.uikit.ECommerceTexField
import androidx.compose.ui.platform.LocalContext

@Composable
fun RegisterScreen(
    uiState: RegisterContract.UiState,
    onAction: (RegisterContract.UiAction) -> Unit,// fonksiyonu değişken olarak tanımlıyoruz. ve fonksiyonu taşıyabiliriyoruz
    // high order funcs
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        // Hata mesajlarını göster
        uiState.validationErrors.forEach { error ->
            Text(
                text = error,
                color = Color.Red, // Hata mesajları kırmızı renkte
                modifier = Modifier.padding(1.dp)
            )
        }
        ECommerceTexField(
            title = "email",
            value = uiState.email,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnEmailChange(it)) },
            leadingIcon = Icons.Filled.AccountCircle,
        )
        ECommerceTexField(
            title = "password",
            value = uiState.password,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnPasswordChange(it)) },
            leadingIcon = Icons.Filled.Build,
            trailingIcon = {
                TextButton(onClick = { onAction.invoke(RegisterContract.UiAction.OnPasswordVisibilityChange) }) {
                    Text(text = if (uiState.isPasswordVisible) "Hide" else "Show")
                }
            },
            visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        ECommerceTexField(
            title = "name",
            value = uiState.name,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnNameChange(it)) },
            leadingIcon = Icons.Filled.Edit,
        )
        ECommerceTexField(
            title = "phone number",
            value = uiState.phone,
            onTextChange = {
                // Sadece sayısal karakterleri bırak ve 10 karakterle sınırla
                val stripped = it.replace(Regex("[^0-9]"), "")
                val formatted = if (stripped.length >= 10) stripped.substring(0..9) else stripped
                onAction(RegisterContract.UiAction.OnPhoneChange(formatted))
            },
            leadingIcon = Icons.Filled.Phone,
            visualTransformation = NanpVisualTransformation(), // Görsel dönüşümü burada uygula
        )

        ECommerceTexField(
            title = "address",
            value = uiState.address,
            onTextChange = { onAction.invoke(RegisterContract.UiAction.OnAddressChange(it)) },
            leadingIcon = Icons.Filled.Home,
        )
        Button(
            onClick = {
                onAction(RegisterContract.UiAction.OnRegisterClick)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
        ) {
            Text(text = "Register")
        }
    }
}

