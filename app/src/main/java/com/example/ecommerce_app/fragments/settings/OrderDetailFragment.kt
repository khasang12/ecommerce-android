package com.example.ecommerce_app.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.adapters.BillingProductAdapter
import com.example.ecommerce_app.data.OrderStatus
import com.example.ecommerce_app.data.getOrderStatus
import com.example.ecommerce_app.databinding.FragmentOrderDetailBinding
import com.example.ecommerce_app.utils.VerticalItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment: Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingAdapter by lazy {BillingProductAdapter()}
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val order = args.order

        setupOrderDetailRv()

        binding.apply {
            tvOrderId.text = "Order #${order.orderId}"
            tvFullName.text = order.address.fullName
            tvAddress.text= "${order.address.street} ${order.address.city}"
            tvPhoneNumber.text = order.address.phone
            tvTotalprice.text = "$ ${order.totalPrice}"

            imgCloseOrder.setOnClickListener {
                findNavController().navigateUp()
            }

            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Shipped.status,
                    OrderStatus.Delivered.status
                )
            )
            val currentOrderState = when(getOrderStatus(order.orderStatus)){
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                else -> 0
            }
            stepView.go(currentOrderState, false)
            if(currentOrderState == 3){
                stepView.done(true)
            }
        }
        billingAdapter.differ.submitList(order.products)
    }

    private fun setupOrderDetailRv() {
        binding.rvProducts.apply {
            adapter = billingAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
}