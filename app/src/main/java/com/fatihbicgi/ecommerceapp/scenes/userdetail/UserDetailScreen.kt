package com.fatihbicgi.ecommerceapp.scenes.userdetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fatihbicgi.ecommerceapp.scenes.login.LoginContract
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Composable
fun UserDetailScreen(
    uiState: UserDetailContract.UiState,
    viewModel: UserDetailViewModel, //viewmodelin burada isi yok, kaldır
    uiEffect: Flow<UserDetailContract.UiEffect>,
    onLogout: () -> Unit,
) {
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is UserDetailContract.UiEffect.NavigateToLoginScreen -> {
                    onLogout()
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome",
            color = Color.Green
        )
        Text(
            text = uiState.userId,
            color = Color.Black
        )
        Button(onClick = {
            viewModel.onAction(UserDetailContract.OnAction.Logout)
        }) {
            Text("Çıkış Yap")
        }
    }
}
