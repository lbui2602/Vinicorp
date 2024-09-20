package com.example.freshervnc.restaurant.model

data class Order(var listFood : List<Food>, val table: Table, var sumFood:Int, var sumPrice:Int, var state : Boolean)
