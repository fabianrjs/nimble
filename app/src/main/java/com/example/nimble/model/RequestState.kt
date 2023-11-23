package com.example.nimble.model

sealed class RequestState {
    class SuccessWithMessage(val message: String) : RequestState()
    object Success : RequestState()
    object Loading : RequestState()
    object Error : RequestState()
}
