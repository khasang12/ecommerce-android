package com.example.ecommerce_app.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapters.BestProductsAdapter
import com.example.ecommerce_app.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment: Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    private lateinit var offerAdapter: BestProductsAdapter
    private lateinit var bestProductsAdapter: BestProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRv()
        setupBestProductsRv()
    }

    private fun setupOfferRv() {
//        offerAdapter = BestProductsAdapter()
//        binding.rvBestProducts.apply{
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            adapter = offerAdapter
//        }
    }

    private fun setupBestProductsRv() {
//        bestProductsAdapter = BestProductsAdapter()
//        binding.rvBestProducts.apply{
//            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
//            adapter = bestProductsAdapter
//        }
    }

}