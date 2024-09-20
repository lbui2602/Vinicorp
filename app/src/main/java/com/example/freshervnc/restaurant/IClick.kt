package com.example.freshervnc.restaurant

import com.example.freshervnc.restaurant.model.Food
import com.example.freshervnc.restaurant.model.Order
import com.example.freshervnc.restaurant.model.Table

interface IClick {
    fun onClickItemOrder(order: Order, position : Int)
    fun onClickTableUpdate(table: Table,position: Int)
    fun onClickFoodUpdate(food: Food,position: Int)
}