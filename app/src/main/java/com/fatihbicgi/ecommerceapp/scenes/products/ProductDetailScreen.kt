package com.fatihbicgi.ecommerceapp.scenes.products

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fatihbicgi.ecommerceapp.data.remote.products.Product

@Composable
fun ProductDetailScreen(
    uiState: ProductDetailContract.UiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        uiState.product?.let {
            Text(
                text = it.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        uiState.product?.imageOne?.let {
            AsyncImage(
                model = it,
                contentDescription = uiState.product.title,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Price: \$${uiState.product?.price}",
            fontSize = 18.sp,
            color = Color.Gray
        )

        Text(
            text = "Sale Price: \$${uiState.product?.salePrice}",
            fontSize = 18.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        uiState.product?.description?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}