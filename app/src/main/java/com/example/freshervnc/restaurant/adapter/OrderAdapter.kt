package com.example.freshervnc.restaurant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freshervnc.R
import com.example.freshervnc.databinding.LayoutItemOrderBinding
import com.example.freshervnc.restaurant.IClick
import com.example.freshervnc.restaurant.model.Order
import com.google.android.flexbox.FlexboxLayout

class OrderAdapter(private var list : List<Order>, private val context: Context, private val iClick: IClick) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    class OrderViewHolder(val binding: LayoutItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemOrderBinding.inflate(inflater, parent, false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(newList: List<Order>){
        list = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = list[position]
        val foods = order.listFood
        holder.binding.flbFood.removeAllViews()
        for(i in 0..foods.size-1){
            val food = foods.get(i)
            add(food.image,food.quantity.toString(),holder.binding.flbFood, context)
        }
        holder.binding.tvSumFood.setText("Tổng món: "+order.sumFood)
        holder.binding.tvSumPrice.setText("Tổng tiền: "+order.sumPrice)
        holder.binding.tvTableName.setText(order.table.name)
        if (order.state == true){
            holder.binding.llPayed.visibility = View.VISIBLE
        }
        holder.binding.llItemOrder.setOnClickListener{
            if(order.state==false){
                iClick.onClickItemOrder(order,position)
            }
        }

    }
    private fun add(imageResId: Int, text: String, mainLayout: FlexboxLayout, context: Context) {
        val inflater = LayoutInflater.from(context)
        val itemLayout = inflater.inflate(R.layout.layout_image_food, mainLayout, false) as FrameLayout
        val textView = itemLayout.findViewById<TextView>(R.id.tvQuantity)
        textView.text = text
        val imageView = itemLayout.findViewById<ImageView>(R.id.imgFood)
        imageView.setImageResource(imageResId)
        mainLayout.addView(itemLayout)
    }
}