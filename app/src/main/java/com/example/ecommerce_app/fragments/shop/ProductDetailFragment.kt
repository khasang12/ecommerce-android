package com.example.ecommerce_app.fragments.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activities.ShopActivity
import com.example.ecommerce_app.adapters.ColorAdapter
import com.example.ecommerce_app.adapters.DetailViewpagerAdapter
import com.example.ecommerce_app.adapters.SizeAdapter
import com.example.ecommerce_app.databinding.FragmentProductdetailBinding
import com.example.ecommerce_app.utils.hideBottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductDetailFragment: Fragment() {
    private val args by navArgs<ProductDetailFragmentArgs>()
    private lateinit var binding: FragmentProductdetailBinding
    private val viewpagerAdapter by lazy { DetailViewpagerAdapter() }
    private val sizeAdapter by lazy { SizeAdapter() }
    private val colorAdapter by lazy { ColorAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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