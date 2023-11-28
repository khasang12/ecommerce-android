package com.example.ecommerce_app.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerce_app.R
import com.example.ecommerce_app.databinding.ActivityShopBinding
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.viewmodel.CartViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ShopActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityShopBinding.inflate(layoutInflater)
    }

    val viewModel by viewModels<CartViewModel>()

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = Navigation.findNavController(this, R.id.shoppingHostFragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        lifecycleScope.launchWhenStarted {
            viewModel.cartProducts.collectLatest {
                when(it){
                    is Resource.Success -> {
                        val count = it.data?.size ?: 0
                        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
                        bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply{
                            number = count
                            backgroundColor = resources.getColor(R.color.g_blue)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}