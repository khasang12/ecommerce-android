package com.example.ecommerce_app.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activities.ShopActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShopActivity).findViewById<BottomNavigationView>(com.example.ecommerce_app.R.id.bottom_navigation)
    bottomNavigationView.visibility = android.view.View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShopActivity).findViewById<BottomNavigationView>(com.example.ecommerce_app.R.id.bottom_navigation)
    bottomNavigationView.visibility = android.view.View.VISIBLE
}