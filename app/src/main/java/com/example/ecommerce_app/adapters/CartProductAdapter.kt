package com.example.ecommerce_app.adapters

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce_app.data.CartProduct
import com.example.ecommerce_app.data.Product
import com.example.ecommerce_app.databinding.CartProductItemBinding
import com.example.ecommerce_app.utils.getProductPrice

class CartProductAdapter: RecyclerView.Adapter<CartProductAdapter.CartProductViewHolder>() {

    inner class CartProductViewHolder(val binding: CartProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(cartProduct: CartProduct){
            binding.apply{
                Glide.with(itemView).load(cartProduct.product.images[0]).into(ivCartProduct)
                tvCartName.text = cartProduct.product.name
                tvCartQuantity.text = cartProduct.quantity.toString()

                val priceAfterPercentage = cartProduct.product.offerPercentage.getProductPrice(cartProduct.product.price)
                tvCartPrice.text = "$ ${String.format("%.2f",priceAfterPercentage)}"
                ivCartColor.setImageDrawable(ColorDrawable(cartProduct.selectedColor?: Color.TRANSPARENT))
                tvCartSize.text = cartProduct.selectedSize ?: "".also{ ivCartSize.setImageDrawable(ColorDrawable(Color.TRANSPARENT)) }
            }
        }
    }

    private val diffCallback = object: DiffUtil.ItemCallback<CartProduct>(){
        override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        return CartProductViewHolder(
            CartProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val cartProduct = differ.currentList[position]
        holder.bind(cartProduct)

        holder.itemView.setOnClickListener {
            onProductClick?.invoke(cartProduct)
        }
        holder.binding.ivPlus.setOnClickListener {
            onPlusClick?.invoke(cartProduct)
        }
        holder.binding.ivMinus.setOnClickListener {
            onMinusClick?.invoke(cartProduct)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onProductClick: ((CartProduct)->Unit)? = null
    var onPlusClick: ((CartProduct)->Unit)? = null
    var onMinusClick: ((CartProduct)->Unit)? = null
}