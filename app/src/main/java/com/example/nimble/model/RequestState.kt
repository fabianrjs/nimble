package com.example.nimble.model

sealed class RequestState {
    object Success : RequestState()
    object Loading : RequestState()
    object Error : RequestState()
}
