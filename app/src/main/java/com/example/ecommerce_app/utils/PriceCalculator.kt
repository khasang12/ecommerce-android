package com.example.ecommerce_app.utils

fun Float?.getProductPrice(price: Float): Float {
    if (this == null) return price
    val remainingPricePercentage = 1f - this/100f
    return remainingPricePercentage * price
}