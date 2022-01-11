package com.enjo.hoefsmidenjo.screens.clients.get

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
import com.enjo.hoefsmidenjo.database.user.DbUser
import com.enjo.hoefsmidenjo.databinding.EventItemBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientGetBinding
import com.enjo.hoefsmidenjo.databinding.FragmentClientItemBinding
import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController

class ClientAdapter(val clickListener: ClientListener) : ListAdapter<DbUser, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: FragmentClientItemBinding, val online:Boolean): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind(clickListener: ClientListener, item: DbUser) {

        if(!online){
            binding.imgDelete.visibility = View.INVISIBLE
        }else{
            binding.imgDelete.visibility = View.VISIBLE
        }

        binding.clientName.text = item.firstName + " " + item.lastName
        binding.clientEmail.text = item.email
        binding.clientPhone.text = item.phone
        binding.clickListener = clickListener

        binding.user = item
        binding.executePendingBindings()



    }

    /**
     * Layout binding for element
     */
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = FragmentClientItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, DomainController.instance.checkForInternet(parent.context))
        }
    }
}
class EventDiffCallback: DiffUtil.ItemCallback<DbUser>(){
    override fun areItemsTheSame(oldItem: DbUser, newItem: DbUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DbUser, newItem: DbUser): Boolean {
        return oldItem.id == newItem.id
        //works perfectly because it's a dataclass.
    }
}

class ClientListener(val clickListener: (User: DbUser)->Unit){
    fun onClick(user: DbUser) = clickListener(user)
}


