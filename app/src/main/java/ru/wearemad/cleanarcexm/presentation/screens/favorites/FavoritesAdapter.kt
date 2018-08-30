package ru.wearemad.cleanarcexm.presentation.screens.favorites

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.extensions.bindView

class FavoritesAdapter(
        context: Context,
        val data: MutableList<Contact>
) : RecyclerView.Adapter<FavoritesAdapter.ItemHolder>() {

    private val inflater = LayoutInflater.from(context)
    var onItemClick: (Long) -> Unit = {}
    var onFavoriteClick: (Long) -> Unit = {}

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

        private val tvName by bindView<TextView>(R.id.tvName)
        private val tvPhone by bindView<TextView>(R.id.tvPhone)
        private val ivFavorite by bindView<ImageView>(R.id.ivFavorite)

        init {
            view.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick.invoke(data[adapterPosition].id)
                }
            }
            ivFavorite.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClick.invoke(data[adapterPosition].id)
                    data.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }

        fun populate(item: Contact) {
            tvPhone.text = item.phone
            tvName.text = "${item.name} ${item.surname}"
            ivFavorite.setImageResource(R.drawable.baseline_star_black_24)
        }
    }
}