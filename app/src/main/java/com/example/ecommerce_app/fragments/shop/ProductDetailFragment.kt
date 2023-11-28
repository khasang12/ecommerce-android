package com.example.ecommerce_app.fragments.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activities.ShopActivity
import com.example.ecommerce_app.adapters.ColorAdapter
import com.example.ecommerce_app.adapters.DetailViewpagerAdapter
import com.example.ecommerce_app.adapters.SizeAdapter
import com.example.ecommerce_app.data.CartProduct
import com.example.ecommerce_app.databinding.FragmentProductdetailBinding
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.utils.hideBottomNavigationView
import com.example.ecommerce_app.viewmodel.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailFragment: Fragment() {
    private val args by navArgs<ProductDetailFragmentArgs>()
    private lateinit var binding: FragmentProductdetailBinding
    private val viewpagerAdapter by lazy { DetailViewpagerAdapter() }
    private val sizeAdapter by lazy { SizeAdapter() }
    private val colorAdapter by lazy { ColorAdapter() }
    private val selectedColor: Int? = null
    private val selectedSize: String? = null
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideBottomNavigationView()
        binding = FragmentProductdetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupColorsRv()
        setupViewpager()

        binding.ivClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDesc.text = product.description

            viewpagerAdapter.differ.submitList(product.images)
            if (product.colors.isNullOrEmpty()) tvProductColors.visibility = View.INVISIBLE
            if (product.sizes.isNullOrEmpty()) tvProductSizes.visibility = View.INVISIBLE
        }

        binding.buttonAddToCart.setOnClickListener{
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.buttonAddToCart.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonAddToCart.revertAnimation()
                        binding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.black))
                    }
                    is Resource.Error -> {
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        product.colors?.let{
            colorAdapter.differ.submitList(it)
        }
        product.sizes?.let{
            sizeAdapter.differ.submitList(it)
        }
    }

    private fun setupViewpager() {
        binding.apply {
            vpProductImages.adapter = viewpagerAdapter
        }
    }

    private fun setupColorsRv() {
        binding.rvColors.apply {
            adapter = colorAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizeAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}