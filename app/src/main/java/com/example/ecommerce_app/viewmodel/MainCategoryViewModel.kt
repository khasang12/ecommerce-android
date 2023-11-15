package com.example.ecommerce_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_app.data.Product
import com.example.ecommerce_app.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts: StateFlow<Resource<List<Product>>> = _bestProducts

    private val _bestDeals = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDeals: StateFlow<Resource<List<Product>>> = _bestDeals

    init {
        fetchSpecialProducts()
        fetchBestProducts()
        fetchBestDeals()
    }

    private fun fetchBestProducts() {
        viewModelScope.launch{
            _bestProducts.emit(Resource.Loading())
        }
        firestore
            .collection("Products")
            .whereEqualTo("category","Special Products").get().addOnSuccessListener { result->
                val bestProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Success(bestProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    private fun fetchBestDeals() {
        viewModelScope.launch{
            _bestDeals.emit(Resource.Loading())
        }
        firestore
            .collection("Products")
            .whereEqualTo("category","Best Deals").get().addOnSuccessListener { result->
                val bestProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Success(bestProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDeals.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    private fun fetchSpecialProducts(){
        viewModelScope.launch{
            _specialProducts.emit(Resource.Loading())
        }
        firestore
            .collection("Products")
            .whereEqualTo("category","Special Products").get().addOnSuccessListener { result->
                val specialProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}