package com.fatihbicgi.ecommerceapp.uikit

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun ECommerceTexField(
    title: String,
    value: String,
    onTextChange: (String) -> Unit,
    leadingIcon: ImageVector,
    trailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onTextChange,

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
        visualTransformation = visualTransformation,
    )
}