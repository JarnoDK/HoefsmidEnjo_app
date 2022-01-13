package com.enjo.hoefsmidenjo.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enjo.hoefsmidenjo.database.relations.RelUserEvent
import com.enjo.hoefsmidenjo.databinding.EventItemBinding

class EventAdapter() : ListAdapter<RelUserEvent, ViewHolder>(EventDiffCallback()){

    //fill up the item you need (e.g. set texts and images)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}


class ViewHolder(val binding: EventItemBinding): RecyclerView.ViewHolder(binding.root){

    /**
     * Gives each element in the layout the correct text
     */
    fun bind(item: RelUserEvent) {

        binding.eventHour.text = item.event.time.substring(11)
        binding.eventTitel.text = item.event.title
        binding.eventLocation.text = item.event.location
        binding.eventClient.text = item.user.firstName + " " + item.user.lastName

        binding.relUserEvent = item
        binding.executePendingBindings()


    }

    /**
     * Layout binding for element
     */
    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = EventItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}
class EventDiffCallback: DiffUtil.ItemCallback<RelUserEvent>(){
    override fun areItemsTheSame(oldItem: RelUserEvent, newItem: RelUserEvent): Boolean {
        return oldItem.event.id == newItem.event.id
    }

    override fun areContentsTheSame(oldItem: RelUserEvent, newItem: RelUserEvent): Boolean {
        return oldItem.event == newItem.event
        //works perfectly because it's a dataclass.
    }
}
