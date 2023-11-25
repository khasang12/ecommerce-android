package com.example.ecommerce_app.fragments.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.adapters.BestProductsAdapter
import com.example.ecommerce_app.databinding.FragmentBaseCategoryBinding

open class BaseCategoryFragment: Fragment(R.layout.fragment_base_category) {
    private lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductsAdapter by lazy {BestProductsAdapter()}
    protected val bestProductsAdapter: BestProductsAdapter by lazy{BestProductsAdapter()}

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

        offerAdapter.onClick = {
            val b = Bundle().apply {putParcelable("product", it)}
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment, b)
        }

        bestProductsAdapter.onClick = {
            val b = Bundle().apply {putParcelable("product", it)}
            findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment, b)
        }

        binding.rvOffer.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!recyclerView.canScrollVertically(1) && dx != 0){
                    onOfferPagingReq()
                }
            }
        })

        binding.nestedScrollBaseCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v, _, scrollY, _, _->
            // check if reach bottom
            if(v.getChildAt(0).bottom <= v.height*scrollY){
                onBestProductsPagingReq()
            }
        })
    }

    fun showOfferLoading(){
        binding.baseProgressbar.visibility = View.VISIBLE
    }

    fun hideOfferLoading(){
        binding.baseProgressbar.visibility = View.GONE
    }

    fun showBestProdLoading(){
        binding.bestprodProgressbar.visibility = View.VISIBLE
    }

    fun hideBestProdLoading(){
        binding.bestprodProgressbar.visibility = View.GONE
    }

    open fun onOfferPagingReq(){

    }

    open fun onBestProductsPagingReq(){

    }

    private fun setupOfferRv() {
        binding.rvOffer.apply{
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    private fun setupBestProductsRv() {
        binding.rvBestProducts.apply{
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductsAdapter
        }
    }

}