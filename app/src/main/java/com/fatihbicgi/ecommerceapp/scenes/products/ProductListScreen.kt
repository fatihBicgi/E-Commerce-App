package com.fatihbicgi.ecommerceapp.scenes.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.fatihbicgi.ecommerceapp.data.remote.products.Product
import com.fatihbicgi.ecommerceapp.uikit.ECommerceTexField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@Composable
fun ProductListScreen(
    uiState: ProductListContract.UiState,
    uiEffect: Flow<ProductListContract.UiEffect>,
    onAction: (ProductListContract.UiAction) -> Unit
) {
    val context = LocalContext.current

    // UI Efektlerini dinleyelim
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is ProductListContract.UiEffect.ShowErrorMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                ProductListContract.UiEffect.NavigateToProductDetailScreen -> {
                    // Navigation işlemi
                }
            }
        }
    }

    // UI ekranı
    if (uiState.errorMessage.isNotEmpty()) {
        Text(text = uiState.errorMessage, color = Color.Red)
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Kategori Ürünleri
            item {
                Text(
                    text = "Category: Notebook",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categoryProducts) { product ->
                        ProductItem(product)
                    }
                }
            }

            // Ürünler Listesi
            item {
                Text(
                    text = "Products",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductItem(product)
                    }
                }
            }

            // İndirimli Ürünler
            item {
                Text(
                    text = "Sale Products",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.saleProducts) { product ->
                        ProductItem(product)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .width(300.dp) // Fixed width for each product item
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp) // Inner padding
    ) {
        Column {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
            product.imageOne?.let {
                AsyncImage(
                    model = it,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp) // Fixed height for image consistency
                        .width(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(4.dp)) // Spacer between title and price
            Text(
                text = "Price: \$${product.price}",
                fontSize = 14.sp
            )
            Text(
                text = "Sale Price: \$${product.salePrice}",
                fontSize = 14.sp
            )
            product.description?.let {
                Spacer(modifier = Modifier.height(8.dp)) // Spacer before description
                Text(
                    text = it,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

