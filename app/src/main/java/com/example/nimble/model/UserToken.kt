package com.example.nimble.model

data class UserToken(
    val id: String,
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Long,
    val refreshToken: String,
    val createdAt: Long
)

fun UserToken.expiresAt() = createdAt + expiresIn
