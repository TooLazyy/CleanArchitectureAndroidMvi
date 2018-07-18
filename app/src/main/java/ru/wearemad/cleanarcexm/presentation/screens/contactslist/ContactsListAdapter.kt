package ru.wearemad.cleanarcexm.presentation.screens.contactslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.TextView
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.domain.global.models.Contact

class ContactsListAdapter(
        context: Context,
        private val data: MutableList<Contact>
) : RecyclerView.Adapter<ContactsListAdapter.ItemHolder>() {

    private val inflater = LayoutInflater.from(context)
    var onItemClick: (Long) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(inflater.inflate(R.layout.item_contact, parent, false))
    }

    override fun getItemCount() = data.size

    fun updateData(list: List<Contact>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.populate(data[position])
    }

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<TextView>(R.id.tvName)
        private val tvPhone = view.findViewById<TextView>(R.id.tvPhone)

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick.invoke(data[adapterPosition].id)
                }
            }
        }

        fun populate(item: Contact) {
            tvPhone.text = item.phone
            tvName.text = "${item.name} ${item.surname}"
        }
    }
}