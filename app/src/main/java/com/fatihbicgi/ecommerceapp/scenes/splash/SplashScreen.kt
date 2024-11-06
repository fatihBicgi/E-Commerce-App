package com.fatihbicgi.ecommerceapp.scenes.splash

import android.content.SharedPreferences
import android.provider.CalendarContract.Colors
import android.widget.Button
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fatihbicgi.ecommerceapp.R

@Composable
fun SplashScreen(
    onGoToLoginScreen: () -> Unit,
    onGoToRegisterScreen: () -> Unit,
    onGoToUserDetailScreen: (String) -> Unit,
    sharedPref: SharedPreferences,
) {
    LaunchedEffect(Unit) {
        // `SharedPreferences`'ten `rememberMe` ve `userId` değerlerini al
        val isRemembered = sharedPref.getBoolean("rememberMe", false)
        val savedUserId = sharedPref.getString("userId", null)

        if (isRemembered && savedUserId != null) {
            // `rememberMe` true ve `userId` kayıtlı ise UserDetailScreen'e yönlendir
            onGoToUserDetailScreen(savedUserId)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {
        LoginImage()
        WelcomeText()
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    onGoToLoginScreen()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
            )
            {
                Text(text = "Login")
            }
            Button(
                onClick = {
                    onGoToRegisterScreen()
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
}


@Composable
fun LoginImage() {
    Image(
        painter = painterResource(R.drawable.login),
        contentDescription = "image of login",
        modifier = Modifier
            .size(240.dp)

    )
}

@Composable
fun WelcomeText() {
    Text(
        text = "Welcome to my E-Commerce App",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )

}

