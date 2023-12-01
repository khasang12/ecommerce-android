package com.example.ecommerce_app.fragments.shop

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapters.AddressAdapter
import com.example.ecommerce_app.adapters.BillingProductAdapter
import com.example.ecommerce_app.data.Address
import com.example.ecommerce_app.data.CartProduct
import com.example.ecommerce_app.data.Order
import com.example.ecommerce_app.data.OrderStatus
import com.example.ecommerce_app.databinding.FragmentBillingBinding
import com.example.ecommerce_app.utils.HorizontalItemDecoration
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.viewmodel.BillingViewModel
import com.example.ecommerce_app.viewmodel.OrderViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BillingFragment: Fragment() {
    private lateinit var binding: FragmentBillingBinding
    private val addressAdapter by lazy { AddressAdapter() }
    private val billingProductAdapter by lazy { BillingProductAdapter() }
    private val billingViewModel by viewModels<BillingViewModel>()
    private val args by navArgs<BillingFragmentArgs>()
    private var products = emptyList<CartProduct>()
    private var totalPrice = ""
    private var selectedAddress: Address? = null
    private val orderViewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        products = args.products.toList()
        totalPrice = args.totalPrice
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBillingProductRv()
        setupAddressRv()

        if(!args.payment){
            binding.apply {
                btnPlaceOrder.visibility = View.INVISIBLE
                totalBoxContainer.visibility = View.INVISIBLE
                line.visibility = View.INVISIBLE
                line2.visibility = View.INVISIBLE
            }
        }

        binding.imgAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_billingFragment_to_addressFragment)
        }

        lifecycleScope.launchWhenStarted {
            billingViewModel.address.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.progressbarAddresses.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        addressAdapter.differ.submitList(it.data)
                        binding.progressbarAddresses.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        binding.progressbarAddresses.visibility = View.GONE
                        Toast.makeText(requireContext(),"Error ${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            orderViewModel.order.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.btnPlaceOrder.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.btnPlaceOrder.revertAnimation()
                        findNavController().navigateUp()
                        Snackbar.make(requireView(),"Your order was created", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        binding.btnPlaceOrder.revertAnimation()
                        Toast.makeText(requireContext(),"Error ${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        billingProductAdapter.differ.submitList(products)
        binding.tvTotalPrice.text = "$ $totalPrice"

        binding.imgAddressClose.setOnClickListener {
            findNavController().navigateUp()
        }

        addressAdapter.onClick = {
            selectedAddress = it
            val b = Bundle().apply{
                putParcelable("address",selectedAddress)
            }
            findNavController().navigate(R.id.action_billingFragment_to_addressFragment, b)
        }

        binding.btnPlaceOrder.setOnClickListener {
            if(selectedAddress == null){
                Toast.makeText(requireContext(),"Please select your address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showOrderConfirmationDialog()
        }
    }

    private fun showOrderConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(requireContext()).apply{
            setTitle("Order items")
            setMessage("Do you want to order your cart items?")
            setNegativeButton("Cancel"){ dialog,_ ->
                dialog.dismiss()
            }
            setPositiveButton("Yes"){ dialog,_ ->
                val order = Order(
                    OrderStatus.Ordered.status,
                    totalPrice.toFloat(),
                    products,
                    selectedAddress!!
                )
                orderViewModel.placeOrder(order)
                dialog.dismiss()
            }
        }
        alertDialog.create()
        alertDialog.show()
    }

    private fun setupAddressRv() {
        binding.rvAddresses.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            adapter = addressAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }

    private fun setupBillingProductRv() {
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            adapter = billingProductAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }
}