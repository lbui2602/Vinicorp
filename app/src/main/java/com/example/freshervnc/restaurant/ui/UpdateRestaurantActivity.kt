package com.example.freshervnc.restaurant.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshervnc.R
import com.example.freshervnc.databinding.ActivityUpdateRestaurantBinding
import com.example.freshervnc.databinding.DialogEditTableBinding
import com.example.freshervnc.databinding.DialogUpdateBinding
import com.example.freshervnc.databinding.LayoutItemFoodUpdateBinding
import com.example.freshervnc.restaurant.IClick
import com.example.freshervnc.restaurant.adapter.FoodAdapter
import com.example.freshervnc.restaurant.adapter.FoodUpdateAdapter
import com.example.freshervnc.restaurant.adapter.OrderAdapter
import com.example.freshervnc.restaurant.adapter.TableAdapter
import com.example.freshervnc.restaurant.adapter.TableUpdateAdapter
import com.example.freshervnc.restaurant.model.Food
import com.example.freshervnc.restaurant.model.Order
import com.example.freshervnc.restaurant.model.Table
import com.example.freshervnc.restaurant.viewmodel.RestaurantViewModel
import com.example.freshervnc.util.DialogUtil

class UpdateRestaurantActivity : AppCompatActivity(),IClick {
    lateinit var binding: ActivityUpdateRestaurantBinding
    private val viewModel : RestaurantViewModel by viewModels()
    lateinit var foodAdapter : FoodUpdateAdapter
    lateinit var tableAdapter: TableUpdateAdapter
    lateinit var bindingEditTable : DialogEditTableBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAdapter()
        binding.llAddTable.setOnClickListener {
            showDialogEditTable(null,false,null)
        }
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.listTable.observe(this, Observer { listTable->
            tableAdapter.updateList(listTable)
        })
    }

    fun setUpAdapter(){
        binding.rcvFood.layoutManager = LinearLayoutManager(this)
        foodAdapter = FoodUpdateAdapter(viewModel.listFoodCurrent.value!!,this)
        binding.rcvFood.adapter = foodAdapter

        binding.rcvTable.layoutManager = GridLayoutManager(this,6)
        tableAdapter = TableUpdateAdapter(viewModel.listTable.value!!,this,this)
        binding.rcvTable.adapter = tableAdapter
    }
    fun showDialogEditTable(table: Table ? = null, isEdit:Boolean,position: Int?=null){
        bindingEditTable = DialogEditTableBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(bindingEditTable.root)
        var alertDialog = dialogBuilder.create()
        if(!isEdit){
            bindingEditTable.tvUpdate.setText("Them")
            bindingEditTable.tvDelete.visibility = View.GONE
        }else{
            bindingEditTable.edtTableName.setText(table!!.name)
            bindingEditTable.tvUpdate.setText("Cap nhat")
            bindingEditTable.tvDelete.visibility = View.VISIBLE
            bindingEditTable.tvDelete.setOnClickListener {
                var lisTable = viewModel.listTable.value as MutableList<Table>
                lisTable.removeAt(position!!)
                viewModel.listTable.value=lisTable
                alertDialog.dismiss()
            }
        }
        bindingEditTable.tvCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        bindingEditTable.tvUpdate.setOnClickListener {
            val tableName = bindingEditTable.edtTableName.text.toString()
            if(tableName.equals("")){
                DialogUtil.showDialog(
                    this,
                    "Hay nhap ban",
                    "OK",
                    null,
                    null,
                )
            }else{
                if(!isEdit){
                    var lisTable = viewModel.listTable.value as MutableList<Table>
                    lisTable.add(Table(tableName,false))
                    viewModel.listTable.value=lisTable
                    alertDialog.dismiss()
                }else{
                    var lisTable = viewModel.listTable.value as MutableList<Table>
                    lisTable[position!!].name = tableName
                    viewModel.listTable.value=lisTable
                    tableAdapter.notifyItemChanged(position)
                    alertDialog.dismiss()
                }
            }
        }
        alertDialog.show()
    }
    fun showDialogEditFood(){
    }
    override fun onClickItemOrder(order: Order, position: Int) {
    }
    override fun onClickTableUpdate(table: Table, position: Int) {
        showDialogEditTable(table,true,position)
    }
    override fun onClickFoodUpdate(food: Food, position: Int) {
    }
}