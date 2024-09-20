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
import com.example.freshervnc.restaurant.IClick
import com.example.freshervnc.restaurant.model.Table
import com.example.freshervnc.restaurant.viewmodel.RestaurantViewModel

class TableUpdateAdapter(private var list : List<Table>,val iClick: IClick,val context: Context) : RecyclerView.Adapter<TableUpdateAdapter.TableUpdateViewHolder>() {
    class TableUpdateViewHolder(val binding: LayoutTableItemBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableUpdateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutTableItemBinding.inflate(inflater, parent, false)
        return TableUpdateViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TableUpdateViewHolder, position: Int) {
        val table = list.get(position)
        if (table!=null){
            holder.binding.btnTable.setText(table.name)
            changeColor(holder.binding.btnTable,R.color.blue,context)
            holder.binding.btnTable.setOnClickListener {
                iClick.onClickTableUpdate(table,position)
            }
        }
    }
    private fun changeColor(button: Button, color:Int,context: Context){
        val background = button.background as GradientDrawable
        background.setColor(ContextCompat.getColor(context, color))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(listTable: List<Table>?) {
        this.list= listTable!!
        notifyDataSetChanged()
    }
}