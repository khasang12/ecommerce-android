package com.example.ecommerce_app.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerce_app.R
import com.example.ecommerce_app.activities.ShopActivity
import com.example.ecommerce_app.databinding.FragmentLoginBinding
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply{
            buttonLoginpageLogin.setOnClickListener{
                val email = etRegEmail.text.toString().trim()
                val password = etRegPassword.text.toString().trim()
                viewModel.loginExec(email,password)
            }
        }

        binding.tvRegLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        lifecycleScope.launch{
            viewModel.login.collect{
                when(it){
                    is Resource.Loading -> {
                        binding.buttonLoginpageLogin.startAnimation()
                    }
                    is Resource.Success -> {
                        binding.buttonLoginpageLogin.revertAnimation()
                        Intent(requireActivity(),ShopActivity::class.java).also{ intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    is Resource.Error -> {
                        binding.buttonLoginpageLogin.revertAnimation()
                        Toast.makeText(requireContext(),it.msg,Toast.LENGTH_LONG).show()
                    }
                    else -> {}
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}