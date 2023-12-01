package com.example.ecommerce_app.fragments.shop

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecommerce_app.BuildConfig
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activities.AuthActivity
import com.example.ecommerce_app.databinding.FragmentProfileBinding
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.utils.showBottomNavigationView
import com.example.ecommerce_app.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }

        binding.linearOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_allOrdersFragment)
        }

        binding.linearBilling.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToBillingFragment(0f.toString(), emptyArray())
            findNavController().navigate(action)
        }

        binding.linearOut.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.tvVersionCode.text = "Version ${BuildConfig.VERSION_CODE}"

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when(it){
                    is Resource.Success->{
                        binding.progressbarSettings.visibility = View.INVISIBLE
                        Glide.with(requireView()).load(it.data!!.imagePath).error(ColorDrawable(
                            Color.BLACK)).into(binding.imgUser)
                        binding.tvUserName.text = "${it.data.firstName} ${it.data.lastName}"
                    }
                    is Resource.Loading->{
                        binding.progressbarSettings.visibility = View.VISIBLE
                    }
                    is Resource.Error->{
                        binding.progressbarSettings.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(),it.msg.toString(), Toast.LENGTH_SHORT).show()
                    }
                    else->Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showBottomNavigationView()
    }
}