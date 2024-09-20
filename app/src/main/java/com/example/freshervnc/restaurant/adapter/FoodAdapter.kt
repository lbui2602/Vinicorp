package com.example.freshervnc.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freshervnc.databinding.LayoutItemFoodBinding
import com.example.freshervnc.restaurant.model.Food

class FoodAdapter(private var list : List<Food>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(val binding: LayoutItemFoodBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemFoodBinding.inflate(inflater, parent, false)
        return FoodViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = list.get(position)
        if(food!=null){
            holder.binding.tvNameFood.setText(food.name)
            holder.binding.tvQuantityItem.setText(food.quantity.toString())
            holder.binding.imgFoodItem.setImageResource(food.image)
            holder.binding.btnPlus.setOnClickListener {
                var quantity = holder.binding.tvQuantityItem.text.toString().toInt()
                quantity+=1
                holder.binding.tvQuantityItem.setText(quantity.toString())
                food.quantity = quantity
            }
            holder.binding.btnMinus.setOnClickListener {
                var quantity = holder.binding.tvQuantityItem.text.toString().toInt()
                if(quantity>0){
                    quantity-=1
                    holder.binding.tvQuantityItem.setText(quantity.toString())
                    food.quantity = quantity
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}
