package com.example.freshervnc.restaurant.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshervnc.R
import com.example.freshervnc.restaurant.model.Food
import com.example.freshervnc.restaurant.model.Order
import com.example.freshervnc.restaurant.model.Table

class RestaurantViewModel : ViewModel() {
    private val _listTable = MutableLiveData<List<Table>>()
    private val _listFoodCurrent = MutableLiveData<List<Food>>()
    private val _listOrder = MutableLiveData<List<Order>>()
    private val _tableChoice = MutableLiveData<Table?>()

    val listTable: LiveData<List<Table>> = _listTable
    val listFoodCurrent: LiveData<List<Food>> = _listFoodCurrent
    val listOrder: LiveData<List<Order>> = _listOrder
    val tableChoice: MutableLiveData<Table?> = _tableChoice
    init {
        _listTable.value = mutableListOf<Table>(
            Table("Bàn 1",false),
            Table("Bàn 2",false),
            Table("Bàn 3",false),
            Table("Bàn 4",false),
            Table("Bàn 5",false),
            Table("Bàn 6",false),
            Table("Bàn 7",false),
            Table("Bàn 8",false),
        )
        _listFoodCurrent.value = mutableListOf<Food>(
            Food("Bia", R.drawable.ic_beer,0,7000),
            Food("Lạc",R.drawable.ic_peanut,0,20000),
            Food("Nem rán", R.drawable.ic_spring_roll,0,50000),
            Food("Nước", R.drawable.ic_mineral_water,0,5000),
            Food("Coca", R.drawable.ic_coca,0,10000),
            Food("Trà sữa", R.drawable.ic_milk_tea,0,30000),
            Food("Cà phê", R.drawable.ic_coffee,0,25000)
        )
    }
    fun updateListTable(updatedTable: Table, position: Int) {
        val currentList = _listTable.value?.toMutableList()
        currentList?.let {
            it[position] = updatedTable
            _listTable.value = it
        }
    }

    fun addTable(newTable: Table) {
        val currentList = _listTable.value?.toMutableList()
        currentList?.let {
            it.add(newTable)
            _listTable.value = it
        }
    }
    fun getListFoodCurrent(): List<Food> {
        return _listFoodCurrent.value ?: emptyList()
    }

    fun deleteTable(position: Int) {
        val currentList = _listTable.value?.toMutableList()
        currentList?.let {
            if (position in it.indices) {
                it.removeAt(position)
                _listTable.value = it
            }
        }
    }
    fun resetTableChoice() {
        _tableChoice.value = null
    }

    fun updateListOrder(newOrderList: List<Order>) {
        _listOrder.value = newOrderList
    }

    fun addListOrder(newOrderList: List<Order>) {
        val currentList = _listOrder.value?.toMutableList() ?: mutableListOf()
        currentList.addAll(newOrderList)
        _listOrder.value = currentList
    }
}