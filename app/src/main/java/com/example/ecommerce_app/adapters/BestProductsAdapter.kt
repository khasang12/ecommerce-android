package com.example.ecommerce_app.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecommerce_app.data.Product
import com.example.ecommerce_app.databinding.ProductRvItemBinding
import com.example.ecommerce_app.utils.getProductPrice

class BestProductsAdapter: RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {
    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding): ViewHolder(binding.root){
        fun bind(product: Product){
            binding.apply{
                Glide.with(itemView).load(product.images[0]).into(imgProduct)
                tvName.text = product.name
                if(product.offerPercentage != null){
                    val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)
                    tvNewPrice.visibility = View.VISIBLE
                    tvNewPrice.text = "$ ${String.format("%.2f",priceAfterOffer)}"
                    tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvPrice.text = "$ ${product.price}"
                }
                else{
                    tvNewPrice.visibility = View.INVISIBLE
                    tvPrice.text = "$ ${product.price}"
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Product)->Unit)? = null

}