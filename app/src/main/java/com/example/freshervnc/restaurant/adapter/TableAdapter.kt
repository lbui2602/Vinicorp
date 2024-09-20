package com.example.freshervnc.restaurant.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.freshervnc.R
import com.example.freshervnc.databinding.LayoutTableItemBinding
import com.example.freshervnc.restaurant.viewmodel.RestaurantViewModel
import com.example.freshervnc.restaurant.model.Table

class TableAdapter(private var list : List<Table>, val context: Context, val viewModel: RestaurantViewModel) : RecyclerView.Adapter<TableAdapter.TableViewHolder>() {
    class TableViewHolder(val binding: LayoutTableItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutTableItemBinding.inflate(inflater, parent, false)
        return TableViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val table = list.get(position)
        if (table!=null){
            holder.binding.btnTable.setText(table.name)
            if(table.state == true){
                changeColor(holder.binding.btnTable,R.color.grey)
            }else{
                if(viewModel.tableChoice.value!=null && table.name.equals(viewModel.tableChoice.value!!.name.toString())){
                    changeColor(holder.binding.btnTable,R.color.blue)
                }else{
                    changeColor(holder.binding.btnTable,R.color.green)
                }
            }
            holder.binding.btnTable.setOnClickListener {
                if(table.state != true){
                    viewModel.tableChoice.value = table
                    notifyDataSetChanged()
                }
            }
        }
    }
    private fun changeColor(button: Button, color:Int){
        val background = button.background as GradientDrawable
        background.setColor(ContextCompat.getColor(context, color))
    }
    override fun getItemCount(): Int {
        return list.size
    }
}