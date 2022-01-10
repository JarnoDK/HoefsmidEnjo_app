package com.enjo.hoefsmidenjo.screens.invoices.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.invoice.DbInvoiceLine
import com.enjo.hoefsmidenjo.database.relations.RelInvoiceLineInvoiceItem
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.databinding.EventItemBinding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceDetailItemBinding

class InvoiceLineAdapter() : ListAdapter<RelInvoiceLineInvoiceItem, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: FragmentInvoiceDetailItemBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind(item: RelInvoiceLineInvoiceItem) {

        binding.lineAmount.text = ""+item.invoiceLine.amount
        binding.lineUnitprice.text = "%.2f €".format(item.item.unitPrice)
        binding.lineTotal.text = "%.2f €".format(item.item.unitPrice?.times(item.invoiceLine.amount))
        binding.lineName.text = item.item.name


        binding.relInvoiceLineInvoiceItem = item
        binding.executePendingBindings()


    }

    /**
     * Layout binding for element
     */
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentInvoiceDetailItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}
class EventDiffCallback: DiffUtil.ItemCallback<RelInvoiceLineInvoiceItem>(){
    override fun areItemsTheSame(oldItem: RelInvoiceLineInvoiceItem, newItem: RelInvoiceLineInvoiceItem): Boolean {
        return oldItem.invoiceLine.id == newItem.invoiceLine.id
    }

    override fun areContentsTheSame(oldItem: RelInvoiceLineInvoiceItem, newItem: RelInvoiceLineInvoiceItem): Boolean {
        return oldItem == newItem
        //works perfectly because it's a dataclass.
    }
}


