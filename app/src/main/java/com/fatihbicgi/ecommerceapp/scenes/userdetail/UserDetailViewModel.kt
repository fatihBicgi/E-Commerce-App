package com.fatihbicgi.ecommerceapp.scenes.userdetail

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatihbicgi.ecommerceapp.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val sharedPref: SharedPreferences
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<UserDetailContract.UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    fun onAction(action: UserDetailContract.OnAction) {
        when (action) {
            UserDetailContract.OnAction.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        sharedPref.edit().remove("userId").remove("rememberMe").apply()
        viewModelScope.launch {
            _uiEffect.emit(UserDetailContract.UiEffect.NavigateToLoginScreen)
        }
    }
}