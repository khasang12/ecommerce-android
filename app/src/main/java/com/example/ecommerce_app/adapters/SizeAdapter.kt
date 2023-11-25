package com.example.ecommerce_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.databinding.SizeProductItemBinding

class SizeAdapter : RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {
    private var selectedPos = -1

    inner class SizeViewHolder(val binding: SizeProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(size: String, position: Int){
            binding.tvSize.text = size
            if(position == selectedPos){
                binding.apply{
                    imageShadow.visibility = View.VISIBLE
                }
            } else{
                binding.apply{
                    imageShadow.visibility = View.INVISIBLE
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeAdapter.SizeViewHolder {
        return SizeViewHolder(
            SizeProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SizeAdapter.SizeViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)

        holder.itemView.setOnClickListener {
            selectedPos = holder.adapterPosition
            notifyItemChanged(selectedPos)
            onItemClick?.invoke(size)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((String) -> Unit)? = null
}