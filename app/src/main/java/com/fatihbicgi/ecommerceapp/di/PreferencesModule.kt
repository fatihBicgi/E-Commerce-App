package com.fatihbicgi.ecommerceapp.di

import android.content.Context
import android.content.SharedPreferences
import com.fatihbicgi.ecommerceapp.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    private const val SHARED_PREF_NAME: String = "ecommerce-sharedpref"

    @Provides
    fun provideSharedPref(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}