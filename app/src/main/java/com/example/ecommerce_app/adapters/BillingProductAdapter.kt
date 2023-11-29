package com.example.ecommerce_app.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_app.R
import com.example.ecommerce_app.data.CartProduct
import com.example.ecommerce_app.databinding.BillingProductRvItemBinding
import com.example.ecommerce_app.utils.getProductPrice

class BillingProductAdapter : RecyclerView.Adapter<BillingProductAdapter.BillingProductViewHolder>() {
    private var selectedPos = -1

    inner class BillingProductViewHolder(val binding: BillingProductRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(billingProduct: CartProduct){
            binding.apply {
                Glide.with(itemView).load(billingProduct.product.images[0]).into(ivCartProduct)
                tvCartName.text = billingProduct.product.name
                tvCartQuantity.text = billingProduct.quantity.toString()

                val priceAfterPercentage = billingProduct.product.offerPercentage.getProductPrice(billingProduct.product.price)
                tvCartPrice.text = "$ ${String.format("%.2f",priceAfterPercentage)}"
                ivCartColor.setImageDrawable(ColorDrawable(billingProduct.selectedColor?: Color.TRANSPARENT))
                tvCartSize.text = billingProduct.selectedSize ?: "".also{ ivCartSize.setImageDrawable(ColorDrawable(
                    Color.TRANSPARENT)) }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product == newItem.product
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingProductAdapter.BillingProductViewHolder {
        return BillingProductViewHolder(
            BillingProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    var selectedBillingProduct = -1
    override fun onBindViewHolder(holder: BillingProductAdapter.BillingProductViewHolder, position: Int) {
        val billingProduct = differ.currentList[position]
        holder.bind(billingProduct)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((CartProduct) -> Unit)? = null
}