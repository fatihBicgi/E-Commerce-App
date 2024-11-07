package com.fatihbicgi.ecommerceapp.di


import com.fatihbicgi.ecommerceapp.data.remote.login.LoginService
import com.fatihbicgi.ecommerceapp.data.remote.products.ProductsService
import com.fatihbicgi.ecommerceapp.data.remote.register.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(
            60,
            TimeUnit.SECONDS
        )
        .readTimeout(
            60,
            TimeUnit.SECONDS
        )
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.canerture.com/ecommerce/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    fun provideRegisterService(
        retrofit: Retrofit,
    ): RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    @Provides
    fun provideLoginService(
        retrofit: Retrofit,
    ): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }
}