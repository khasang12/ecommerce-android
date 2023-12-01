package com.example.ecommerce_app.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce_app.R
import com.example.ecommerce_app.data.Order
import com.example.ecommerce_app.data.OrderStatus
import com.example.ecommerce_app.data.getOrderStatus
import com.example.ecommerce_app.databinding.OrderRvItemBinding

class AllOrdersAdapter : RecyclerView.Adapter<AllOrdersAdapter.OrdersViewHolder>() {
    inner class OrdersViewHolder(val binding: OrderRvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order){
            binding.apply {
                tvOrderId.text = order.orderId.toString()
                tvOrderDate.text = order.date
                val resources = itemView.resources
                val colorDrawable = when(getOrderStatus(order.orderStatus)){
                    is OrderStatus.Ordered -> {
                        ColorDrawable(resources.getColor(R.color.g_orange))
                    }
                    is OrderStatus.Confirmed -> {
                        ColorDrawable(resources.getColor(R.color.g_green))
                    }
                    is OrderStatus.Delivered -> {
                        ColorDrawable(resources.getColor(R.color.g_green))
                    }
                    is OrderStatus.Shipped -> {
                        ColorDrawable(resources.getColor(R.color.g_green))
                    }
                    is OrderStatus.Canceled -> {
                        ColorDrawable(resources.getColor(R.color.g_red))
                    }
                    is OrderStatus.Returned -> {
                        ColorDrawable(resources.getColor(R.color.g_red))
                    }
                }
                imgOrderState.setImageDrawable(colorDrawable)
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.orderId == newItem.orderId
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrdersAdapter.OrdersViewHolder {
        return OrdersViewHolder(
            OrderRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AllOrdersAdapter.OrdersViewHolder, position: Int) {
        val order = differ.currentList[position]
        holder.bind(order)

        holder.itemView.setOnClickListener {
            onClick?.invoke(order)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Order) -> Unit)? = null
}