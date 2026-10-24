package com.shoppingapp.store.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R
import com.shoppingapp.store.data.Money
import com.shoppingapp.store.model.Item

class ProductAdapter(
    items: List<Item>,
    private val onClick: (Item) -> Unit
) : RecyclerView.Adapter<ProductAdapter.Holder>() {

    private val items = items.toMutableList()

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.productImage)
        val title: TextView = itemView.findViewById(R.id.productTitle)
        val rating: TextView = itemView.findViewById(R.id.productRating)
        val price: TextView = itemView.findViewById(R.id.productPrice)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submit(next: List<Item>) {
        items.clear()
        items.addAll(next)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.cover)
        holder.title.text = item.title
        holder.rating.text = item.rating.toString()
        holder.price.text = Money.format(item.price)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}
