package com.fatihbicgi.ecommerceapp.scenes.products

import android.widget.Toast
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.fatihbicgi.ecommerceapp.data.remote.products.Product
import kotlinx.coroutines.flow.Flow


@Composable
fun ProductListScreen(
    uiState: ProductListContract.UiState,
    uiEffect: Flow<ProductListContract.UiEffect>,
    uiAction: (ProductListContract.UiAction) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is ProductListContract.UiEffect.ShowErrorMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is ProductListContract.UiEffect.NavigateToProductDetailScreen -> onNavigateToDetail(
                    effect.productId
                )
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

            // Bütün Kategoriler
            item {
                Text(
                    text = "All  Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categories) { category ->
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A65))
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = category.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            AsyncImage(
                                model = category.image,
                                contentDescription = category.image,
                            )
                        }
                    }
                }
            }
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
                        ProductItem(product) {
                            uiAction.invoke(ProductListContract.UiAction.OnProductClicked(product.id))
                            //viewModel.onProductClicked(product.id)
                            //buradan productId'yi viewmodele göndermeliyim ve orada işlemleri yapmalıyım.
                        }
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
                    modifier = Modifier
                        .fillMaxWidth(),

                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductItem(product) {
                            // Ürün tıklanınca ViewModel’e bildirme
                            uiAction.invoke(ProductListContract.UiAction.OnProductClicked(product.id))
                        }
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
                        ProductItem(product) {
                            // Ürün tıklanınca ViewModel’e bildirme
                            uiAction.invoke(ProductListContract.UiAction.OnProductClicked(product.id))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(250.dp)
            .width(250.dp)
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }  // Tıklama olayını yakalama
            .padding(16.dp)
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
                        .height(100.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price: \$${product.price}", fontSize = 14.sp)
            Text(text = "Sale Price: \$${product.salePrice}", fontSize = 14.sp)
            product.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }

}


