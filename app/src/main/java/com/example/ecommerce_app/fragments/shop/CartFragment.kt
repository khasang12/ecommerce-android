package com.example.ecommerce_app.fragments.shop

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapters.CartProductAdapter
import com.example.ecommerce_app.data.CartProduct
import com.example.ecommerce_app.databinding.FragmentCartBinding
import com.example.ecommerce_app.firebase.FirebaseCommon
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.utils.VerticalItemDecoration
import com.example.ecommerce_app.viewmodel.CartViewModel
import kotlinx.coroutines.flow.collectLatest

class CartFragment : Fragment(R.layout.fragment_cart) {
    private lateinit var binding: FragmentCartBinding
    private val cartAdapter by lazy { CartProductAdapter() }
    private val viewModel by activityViewModels<CartViewModel>()// call from activity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCartRv()

        var totalPrice = ""
        lifecycleScope.launchWhenStarted {
            viewModel.productsPrice.collectLatest { price ->
                price?.let{
                    totalPrice = it as String
                    binding.tvTotalPrice.text = "$ $price"
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.cartProducts.collectLatest {
                when(it){
                    is Resource.Success->{
                        binding.progressCart.visibility = View.INVISIBLE
                        if(it.data!!.isEmpty()) {
                            showEmptyCart()
                            hideOtherViews()
                        }
                        else{
                            hideEmptyCart()
                            showOtherViews()
                            cartAdapter.differ.submitList(it.data)
                        }
                    }
                    is Resource.Loading->{
                        binding.progressCart.visibility = View.VISIBLE
                    }
                    is Resource.Error->{
                        binding.progressCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),it.msg.toString(),Toast.LENGTH_SHORT).show()
                    }
                    else->Unit
                }
            }
        }

        cartAdapter.onProductClick = {
            val b = Bundle().apply {putParcelable("product",it.product)}
            findNavController().navigate(R.id.action_cartFragment_to_productDetailFragment, b)
        }

        cartAdapter.onPlusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.INCREASE)
        }

        cartAdapter.onMinusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.DECREASE)
        }

        binding.buttonAddToCart.setOnClickListener {
            val b = Bundle().apply {
                putParcelableArray("products",cartAdapter.differ.currentList.toTypedArray())
                putString("totalPrice",totalPrice)
                putBoolean("payment",true)
            }
            findNavController().navigate(R.id.action_cartFragment_to_billingFragment, b)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.deleteDialog.collectLatest {
                val alertDialog = AlertDialog.Builder(requireContext()).apply{
                    setTitle("Delete item from Cart")
                    setMessage("Do you want to delete this item?")
                    setNegativeButton("Cancel"){ dialog,_ ->
                        dialog.dismiss()
                    }
                    setPositiveButton("Yes"){ dialog,_ ->
                        viewModel.deleteCartProduct(it)
                        dialog.dismiss()
                    }
                }
                alertDialog.create()
                alertDialog.show()
            }
        }

        binding.ivCloseCart.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun hideOtherViews() {
        binding.apply {
            rvCart.visibility = View.GONE
            totalBoxContainer.visibility = View.GONE
            buttonAddToCart.visibility = View.GONE
        }
    }

    private fun showOtherViews() {
        binding.apply {
            rvCart.visibility = View.VISIBLE
            totalBoxContainer.visibility = View.VISIBLE
            buttonAddToCart.visibility = View.VISIBLE
        }
    }

    private fun hideEmptyCart() {
        binding.apply {
            layoutCartEmpty.visibility = View.GONE
        }
    }

    private fun showEmptyCart() {
        binding.apply {
            layoutCartEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupCartRv() {
        binding.rvCart.apply{
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
            adapter = cartAdapter
            addItemDecoration(VerticalItemDecoration())
        }
    }
}