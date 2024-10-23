package com.fatihbicgi.ecommerceapp.uikit

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun ECommerceTexField(
    title: String,
    leadingIcon: ImageVector,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    val textState = rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it },

        label = {
            Text(text = title)
        },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = "")
        },
        trailingIcon = {
            trailingIcon?.invoke()

        },
        singleLine = true,
        visualTransformation = visualTransformation
    )
}