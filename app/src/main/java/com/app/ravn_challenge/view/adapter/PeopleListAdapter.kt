package com.app.ravn_challenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.ravn_challenge.AllPeopleQuery
import com.app.ravn_challenge.databinding.PersonItemBinding

class PeopleListAdapter() : ListAdapter<AllPeopleQuery.Person, PeopleListAdapter.ViewHolder>(PeopleDiffUtil()) {

    var onItemClicked: ((AllPeopleQuery.Person) -> Unit)? = null

    class ViewHolder(val binding: PersonItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person =  getItem(position)
        holder.binding.personName.text = person.name ?: "naida"

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(person)
        }
    }

}

class PeopleDiffUtil : DiffUtil.ItemCallback<AllPeopleQuery.Person>() {

    override fun areItemsTheSame(
        oldItem: AllPeopleQuery.Person,
        newItem: AllPeopleQuery.Person
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AllPeopleQuery.Person,
        newItem: AllPeopleQuery.Person
    ): Boolean {
        return oldItem == newItem
    }

}