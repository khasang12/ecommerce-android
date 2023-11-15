package com.example.ecommerce_app.fragments.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce_app.adapters.HomeViewpagerAdapter
import com.example.ecommerce_app.databinding.FragmentHomeBinding
import com.example.ecommerce_app.fragments.categories.*
import com.google.android.material.tabs.TabLayoutMediator
import com.google.common.collect.Table

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabLayout()
    }

    private fun initTabLayout() {
        val categoryFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            ChairFragment(),
            CupboardFragment(),
            TableFragment(),
            AccessoryFragment(),
            FurnitureFragment()
        )
        binding.vpHome.isUserInputEnabled = false

        val viewpagerAdapter = HomeViewpagerAdapter(categoryFragments, childFragmentManager, lifecycle)
        binding.vpHome.adapter = viewpagerAdapter
        TabLayoutMediator(binding.homeTabLayout,binding.vpHome){ tab, position ->
            when(position){
                0->tab.text="Main"
                1->tab.text="Chair"
                2->tab.text="Cupboard"
                3->tab.text="Table"
                4->tab.text="Accessory"
                5->tab.text="Furniture"
            }
        }.attach()
    }
}