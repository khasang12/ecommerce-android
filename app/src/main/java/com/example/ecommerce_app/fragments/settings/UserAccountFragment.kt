package com.example.ecommerce_app.fragments.settings

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ecommerce_app.data.User
import com.example.ecommerce_app.databinding.FragmentUserAccountBinding
import com.example.ecommerce_app.dialog.setupBottomSheetDialog
import com.example.ecommerce_app.utils.Resource
import com.example.ecommerce_app.viewmodel.UserAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserAccountFragment : Fragment() {
    private lateinit var binding: FragmentUserAccountBinding
    private val viewModel by viewModels<UserAccountViewModel>()
    private lateinit var imageActivityResultLauncher: ActivityResultLauncher<Intent>

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                imageUri = it.data?.data
                Glide.with(this).load(imageUri).into(binding.imgUser)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        hideUserLoading()
                        showUserInfo(it.data!!)
                    }
                    is Resource.Loading -> {
                        showUserLoading()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.msg.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.updateInfo.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        binding.btnSaveProfile.startAnimation()
                        findNavController().navigateUp()
                    }
                    is Resource.Loading -> {
                        binding.btnSaveProfile.startAnimation()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.msg.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> Unit
                }
            }
        }

        binding.btnSaveProfile.setOnClickListener {
            binding.apply {
                val firstname = edFirstName.text.toString().trim()
                val lastname = edLastName.text.toString().trim()
                val email = edEmail.text.toString().trim()
                val user = User(firstname, lastname, email)
                viewModel.updateUser(user, imageUri)
            }
        }

        binding.tvUpdatePassword.setOnClickListener{
            setupBottomSheetDialog {

            }
        }

        binding.ivCloseEditProfile.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imgEdit.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            imageActivityResultLauncher.launch(intent)
        }
    }

    private fun showUserInfo(data: User) {
        binding.apply {
            Glide.with(this@UserAccountFragment).load(data.imagePath)
                .error(ColorDrawable(Color.BLACK)).into(imgUser)
            edFirstName.setText(data.firstName)
            edLastName.setText(data.lastName)
            edEmail.setText(data.email)
        }
    }

    private fun hideUserLoading() {
        binding.apply {
            progressbarEditProfile.visibility = View.GONE
            imgUser.visibility = View.VISIBLE
            imgEdit.visibility = View.VISIBLE
            edEmail.visibility = View.VISIBLE
            edFirstName.visibility = View.VISIBLE
            tvUpdatePassword.visibility = View.VISIBLE
            btnSaveProfile.visibility = View.VISIBLE
        }
    }

    private fun showUserLoading() {
        binding.apply {
            progressbarEditProfile.visibility = View.VISIBLE
            imgUser.visibility = View.INVISIBLE
            imgEdit.visibility = View.INVISIBLE
            edEmail.visibility = View.INVISIBLE
            edFirstName.visibility = View.INVISIBLE
            tvUpdatePassword.visibility = View.INVISIBLE
            btnSaveProfile.visibility = View.INVISIBLE
        }
    }
}