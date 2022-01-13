package com.enjo.hoefsmidenjo.screens.invoices.get

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enjo.hoefsmidenjo.database.relations.RelClientInvoiceAmount
import com.enjo.hoefsmidenjo.databinding.FragmentInvoiceListItemBinding

class InvoiceAdapter(val InvoiceListener: InvoiceDetailListener) : ListAdapter<RelClientInvoiceAmount, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(InvoiceListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: FragmentInvoiceListItemBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind(clickListener: InvoiceDetailListener,item: RelClientInvoiceAmount) {

        binding.invoiceclient.text = "${item.client.firstName} ${item.client.lastName}"
        binding.invoicedate.text = item.invoice.time.substring(0,10)
        binding.invoicepricetotal.text = "%.2f €".format(item.amount)
        binding.clickListener = clickListener


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

class InvoiceDetailListener(val clickListener: (invoice: RelClientInvoiceAmount)->Unit){
    fun onClick(invoice: RelClientInvoiceAmount) = clickListener(invoice)
}