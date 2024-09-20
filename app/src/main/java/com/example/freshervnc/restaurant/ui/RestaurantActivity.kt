package com.example.freshervnc.restaurant.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshervnc.util.DialogUtil
import com.example.freshervnc.databinding.ActivityRestaurantBinding
import com.example.freshervnc.databinding.CustomDialogBinding
import com.example.freshervnc.databinding.DialogUpdateBinding
import com.example.freshervnc.restaurant.IClick
import com.example.freshervnc.restaurant.viewmodel.RestaurantViewModel
import com.example.freshervnc.restaurant.adapter.FoodAdapter
import com.example.freshervnc.restaurant.adapter.OrderAdapter
import com.example.freshervnc.restaurant.adapter.TableAdapter
import com.example.freshervnc.restaurant.model.Food
import com.example.freshervnc.restaurant.model.Order
import com.example.freshervnc.restaurant.model.Table

class RestaurantActivity : AppCompatActivity(), IClick {
    lateinit var binding : ActivityRestaurantBinding
    lateinit var bindingDialog: CustomDialogBinding
    lateinit var bindingDialogUpdate: DialogUpdateBinding
    lateinit var orderAdapter: OrderAdapter
    lateinit var foodAdapter: FoodAdapter
    lateinit var tableAdapter: TableAdapter
    var listOrder = mutableListOf<Order>()
    private val viewModel: RestaurantViewModel by viewModels()
    var listTableCurrent = mutableListOf<Table>()
    var listFoodCurrent = mutableListOf<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()
        listTableCurrent = viewModel.listTable.value!!.toMutableList()
        listFoodCurrent = viewModel.listFoodCurrent.value!!.toMutableList()
        binding.btnAdd.setOnClickListener {
            showCustomDialog(listFoodCurrent,listTableCurrent)
        }
        setUpObservers()
        binding.imgSetting.setOnClickListener {
            startActivity(Intent(this,UpdateRestaurantActivity::class.java))
        }
    }

    private fun setUpAdapter() {
        binding.rcv.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(listOrder,this,this)
        binding.rcv.adapter = orderAdapter
    }

    fun calculateSumPrice(foods: List<Food>): Int {
        return foods.sumBy { it.quantity * it.price }
    }
    private fun showDialog(message: String) {
        DialogUtil.showDialog(
            context = this,
            message = message,
            positiveText = "OK",
            positiveAction = { }
        )
    }
    private fun setUpObservers() {
        viewModel.listOrder.observe(this, Observer { listOrder ->
            orderAdapter.updateList(listOrder)
        })
    }
    private fun showCustomDialog(listfood:List<Food>, listTable: List<Table>) {
        bindingDialog = CustomDialogBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(bindingDialog.root)
        var alertDialog = dialogBuilder.create()
        val listFoodCopy = listfood.map { it.copy() }
        viewModel.listFoodCurrent.value = listFoodCopy
        foodAdapter = FoodAdapter(listfood)
        tableAdapter = TableAdapter(listTable,this,viewModel)
        bindingDialog.rcvTable.layoutManager = GridLayoutManager(this,4)
        bindingDialog.rcvFood.layoutManager = LinearLayoutManager(this)
        bindingDialog.rcvFood.adapter = foodAdapter
        bindingDialog.rcvTable.adapter = tableAdapter
        bindingDialog.btnBook.setOnClickListener {
            order(alertDialog)
        }
        bindingDialog.btnCancel.setOnClickListener {
            alertDialog.dismiss()
            resetData()
        }
        alertDialog.show()
    }
    private fun showCustomDialog(order: Order, listfood: List<Food>, position: Int) {
        bindingDialogUpdate = DialogUpdateBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(bindingDialogUpdate.root)
        var alertDialog = dialogBuilder.create()
        val listFoodCopy = listfood.map { it.copy() }
        listFoodCopy.forEach { food->
            order.listFood.forEach { foodOrder->
                if(food.name.equals(foodOrder.name)){
                    food.quantity = foodOrder.quantity
                }
            }
        }
        foodAdapter = FoodAdapter(listFoodCopy)
        bindingDialogUpdate.rcvFood.layoutManager = LinearLayoutManager(this)
        bindingDialogUpdate.rcvFood.adapter = foodAdapter
        bindingDialogUpdate.btnUpdate.setOnClickListener {
            update(order,listFoodCopy,alertDialog)
            listOrder[position] = order
            orderAdapter.notifyDataSetChanged()
        }
        bindingDialogUpdate.btnCancel.setOnClickListener {
            alertDialog.dismiss()
            resetData()
        }
        bindingDialogUpdate.btnPay.setOnClickListener {
            pay(order,alertDialog)
            listOrder[position] = order
            orderAdapter.notifyDataSetChanged()
            resetData()
        }
        alertDialog.show()
    }
    private fun resetData() {
        listFoodCurrent= (viewModel.listFoodCurrent.value as MutableList<Food>?)!!
        viewModel.tableChoice.value = null
    }
    private fun check(foodList : List<Food>): Boolean {
        return foodList.any { it.quantity > 0 }
    }
    fun getListFoodChooose(foodList: List<Food>): List<Food> {
        return foodList.filter { it.quantity > 0 }
    }
    private fun pay(order: Order,alertDialog: AlertDialog){
        order.state = true
        alertDialog.dismiss()
    }
    private fun update(order: Order, list: List<Food>, alertDialog: AlertDialog) {
        if (!check(list)) {
            showDialog("Phai chon mon khi dat ban")
        } else {
            val listFood = getListFoodChooose(list)
            order.listFood = listFood
            order.sumFood = listFood.size
            order.sumPrice = calculateSumPrice(listFood)
            alertDialog.dismiss()
            resetData()
        }
    }
    private fun order(alertDialog: AlertDialog) {
        if(viewModel.tableChoice.value == null){
            showDialog("Chon ban")
        }else if(!check(listFoodCurrent)){
            showDialog("Chon mon")
        }else{
            val list = getListFoodChooose(listFoodCurrent)
            var listTable = viewModel.listTable.value!!
            listTable.forEach { table->
                if(table.name.equals(viewModel.tableChoice.value!!.name)){
                    table.state = true
                }
            }
            viewModel.listTable.value = listTable
            val order = Order(list,viewModel.tableChoice.value!!,list.size,calculateSumPrice(list),false)
            if(viewModel.listOrder.value != null){
                listOrder = viewModel.listOrder.value!!.toMutableList()
                listOrder.add(order)
                viewModel.listOrder.value = listOrder
            }else{
                listOrder.add(order)
                viewModel.listOrder.value = listOrder
            }
            alertDialog.dismiss()
            resetData()
        }
    }
    override fun onClickItemOrder(order: Order, position : Int) {
        showCustomDialog(order,listFoodCurrent,position)
    }

    override fun onClickTableUpdate(table: Table, position: Int) {

    }

    override fun onClickFoodUpdate(food: Food, position: Int) {

    }
}