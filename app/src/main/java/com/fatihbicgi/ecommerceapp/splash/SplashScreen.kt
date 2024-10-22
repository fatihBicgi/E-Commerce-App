package com.fatihbicgi.ecommerceapp.splash

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatihbicgi.ecommerceapp.R

@Composable
fun SplashScreen () {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,

    )
    {
        Row {
            LoginImage()
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilledButton("Login")
            FilledButton("Register")
        }
    }

}

@Composable
fun FilledButton(title : String){
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
    )
    {
        Text(text =  title)
    }
}
@Composable
fun LoginImage(){
    Image(
        painter = painterResource(R.drawable.login),
        contentDescription = "image of login",
        modifier = Modifier
            .size(240.dp)

    )
}
@Preview(showBackground = true)
@Composable
fun ButtonsPreview(){
    SplashScreen()
}
