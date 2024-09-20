package com.example.freshervnc.restaurant.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freshervnc.databinding.LayoutItemFoodBinding
import com.example.freshervnc.databinding.LayoutItemFoodUpdateBinding
import com.example.freshervnc.restaurant.IClick
import com.example.freshervnc.restaurant.model.Food

class FoodUpdateAdapter(private var list : List<Food>,val iClick: IClick) : RecyclerView.Adapter<FoodUpdateAdapter.FoodUpdateViewHolder>() {
    class FoodUpdateViewHolder(val binding: LayoutItemFoodUpdateBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodUpdateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemFoodUpdateBinding.inflate(inflater, parent, false)
        return FoodUpdateViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FoodUpdateViewHolder, position: Int) {
        val food = list.get(position)
        if(food!=null){
            holder.binding.tvNameFood.setText(food.name)
            holder.binding.tvPrice.setText(food.price.toString())
            holder.binding.imgFoodItem.setImageResource(food.image)
            holder.binding.llFoodUpdate.setOnClickListener {
                iClick.onClickFoodUpdate(food,position)
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size
    }
}