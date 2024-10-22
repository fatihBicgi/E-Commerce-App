package com.fatihbicgi.ecommerceapp.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun LoginScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp, 100.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {
        LoginTextField("mail adress")
        LoginTextField("password")
    }



}
@Composable
fun LoginTextField(title : String){
    val textState = remember { mutableStateOf("") }

    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it},
        label = {
            Text(text = title)
}
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonsPreview(){
    LoginScreen()
}