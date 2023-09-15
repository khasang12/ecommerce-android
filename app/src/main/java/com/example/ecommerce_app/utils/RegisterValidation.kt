package com.example.ecommerce_app.utils

sealed class RegisterValidation(){
    object Success: RegisterValidation()
    data class Failed(val msg: String): RegisterValidation()
}

data class RegisterFieldState(
    val email: RegisterValidation,
    val password: RegisterValidation
)
