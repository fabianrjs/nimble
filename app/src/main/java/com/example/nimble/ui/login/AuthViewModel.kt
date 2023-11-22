package com.example.nimble.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.datastore.AuthStorageRepository
import com.example.nimble.model.RequestState
import com.example.nimble.model.UserToken
import com.example.nimble.network.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val authStorageRepository: AuthStorageRepository
) : ViewModel() {

    private val _emailValue = MutableStateFlow("")
    val emailValue = _emailValue.asStateFlow()

    private val _passwordValue = MutableStateFlow("")
    val passwordValue = _passwordValue.asStateFlow()

    private val _loginRequestState = MutableStateFlow<RequestState?>(null)
    val loginRequestState = _loginRequestState.asStateFlow()

    private val _userToken = MutableStateFlow<UserToken?>(null)
    val userToken = _userToken.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    init {
        getUserAccessToken()
    }

    private fun getUserAccessToken() {
        viewModelScope.launch {
            authStorageRepository.getAccessToken().collect {
                if (it.isNullOrEmpty().not()) {
                    _accessToken.value = it
                    getUserToken()
                }
            }
        }
    }

    // TODO Implement correctly this method
    private fun getUserToken() {
        if (_accessToken.value.isNullOrEmpty().not()){
            _userToken.value = UserToken(
                id = "123456",
                accessToken = _accessToken.value!!,
                tokenType = "Bearer",
                expiresIn = 3600,
                refreshToken = "abcdefgh",
                createdAt = System.currentTimeMillis()
            )
        }
    }

    fun onEmailValueChange(newValue: String) {
        _emailValue.value = newValue
    }

    fun onPasswordValueChange(newValue: String) {
        _passwordValue.value = newValue
    }

    fun login() {
        viewModelScope.launch {
            authRepository.login(_emailValue.value, _passwordValue.value)
                .flowOn(Dispatchers.IO)
                .onStart { _loginRequestState.value = RequestState.Loading }
                .catch { handleLoginRequestError() }
                .collect { response ->
                    if (response.isSuccessful) {
                        response.body()?.data?.let {
                            _loginRequestState.value = RequestState.Success
                            _userToken.value = UserToken(
                                id = it.id,
                                accessToken = it.attributes.accessToken,
                                tokenType = it.attributes.tokenType,
                                expiresIn = it.attributes.expiresIn,
                                refreshToken = it.attributes.refreshToken,
                                createdAt = it.attributes.createdAt
                            )
                            withContext(Dispatchers.IO) {
                                authStorageRepository.saveUserAccessToken(it.attributes.accessToken)
                            }
                        }
                    } else {
                        handleLoginRequestError()
                    }
                }
        }
    }

    private fun handleLoginRequestError() {
        _loginRequestState.value = RequestState.Error
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout(_userToken.value!!.accessToken)
            authStorageRepository.deleteUserAccessToken()
            clearVariables()
        }
    }

    private fun clearVariables() {
        _userToken.value = null
        _accessToken.value = null
        _loginRequestState.value = null
        _emailValue.value = ""
        _passwordValue.value = ""
    }

}
