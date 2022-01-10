package com.enjo.hoefsmidenjo.screens.invoices.get

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enjo.hoefsmidenjo.database.event.DbEvent
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.databinding.EventItemBinding
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListItemBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InvoiceAdapter() : ListAdapter<RelClientInvoiceAmount, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: FragmentInvoiceListItemBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind(item: RelClientInvoiceAmount) {

        binding.invoiceclient.text = "${item.client.firstName} ${item.client.lastName}"
        binding.invoicedate.text = LocalDate.parse(item.invoice.time.substring(0,10)).format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        binding.invoicepricetotal.text = "%.2f €".format(item.amount)



        binding.relClientInvoiceAmount = item
        binding.executePendingBindings()


    }

    /**
     * Layout binding for element
     */
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentInvoiceListItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}
class EventDiffCallback: DiffUtil.ItemCallback<RelClientInvoiceAmount>(){
    override fun areItemsTheSame(oldItem: RelClientInvoiceAmount, newItem: RelClientInvoiceAmount): Boolean {
        return oldItem.invoice.id == newItem.invoice.id
    }

    override fun areContentsTheSame(oldItem: RelClientInvoiceAmount, newItem: RelClientInvoiceAmount): Boolean {
        return oldItem == newItem
        //works perfectly because it's a dataclass.
    }
}
