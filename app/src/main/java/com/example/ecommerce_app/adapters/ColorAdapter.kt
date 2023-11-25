package com.example.ecommerce_app.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.databinding.ColorProductItemBinding

class ColorAdapter : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    private var selectedPos = -1

    inner class ColorViewHolder(val binding: ColorProductItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(color: Int, position: Int){
            val imageDrawable = ColorDrawable(color)
            binding.imageColor.setImageDrawable(imageDrawable)
            if(position == selectedPos){
                binding.apply{
                    imageShadow.visibility = View.VISIBLE
                    ivCheckColor.visibility = View.VISIBLE
                }
            } else{
                binding.apply{
                    imageShadow.visibility = View.INVISIBLE
                    ivCheckColor.visibility = View.INVISIBLE
                }
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Int>(){
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorAdapter.ColorViewHolder {
        return ColorViewHolder(
            ColorProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ColorAdapter.ColorViewHolder, position: Int) {
        val color = differ.currentList[position]
        holder.bind(color, position)

        holder.itemView.setOnClickListener {
            selectedPos = holder.adapterPosition
            notifyItemChanged(selectedPos)
            onItemClick?.invoke(color)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onItemClick: ((Int) -> Unit)? = null
}