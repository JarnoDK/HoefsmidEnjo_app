package com.enjo.hoefsmidenjo.screens.items.get

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enjo.hoefsmidenjo.database.invoiceitem.DbInvoiceItem
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.databinding.EventItemBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientItemBinding
import com.enjo.hoefsmidenjo.databinding.FragmentItemGetListitemBinding

class ItemAdapter() : ListAdapter<DbInvoiceItem, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: FragmentItemGetListitemBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind( item: DbInvoiceItem) {

        binding.itemname.text = item.name
        binding.itemprice.text = "%.2f €".format(item.unitPrice)

        binding.dbInvoiceItem = item
        binding.executePendingBindings()



    }

    /**
     * Layout binding for element
     */
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentItemGetListitemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}
class EventDiffCallback: DiffUtil.ItemCallback<DbInvoiceItem>(){
    override fun areItemsTheSame(oldItem: DbInvoiceItem, newItem: DbInvoiceItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DbInvoiceItem, newItem: DbInvoiceItem): Boolean {
        return oldItem.id == newItem.id
        //works perfectly because it's a dataclass.
    }
}

