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
import com.shoppingapp.store.model.CartLine

class CartAdapter(
    private val onIncrease: (Int) -> Unit,
    private val onDecrease: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.Holder>() {

    private val lines = mutableListOf<CartLine>()

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.lineImage)
        val title: TextView = itemView.findViewById(R.id.lineTitle)
        val model: TextView = itemView.findViewById(R.id.lineModel)
        val price: TextView = itemView.findViewById(R.id.linePrice)
        val quantity: TextView = itemView.findViewById(R.id.lineQty)
        val plus: ImageView = itemView.findViewById(R.id.linePlus)
        val minus: ImageView = itemView.findViewById(R.id.lineMinus)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submit(next: List<CartLine>) {
        lines.clear()
        lines.addAll(next)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_line, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val line = lines[position]
        holder.image.setImageResource(line.item.cover)
        holder.title.text = line.item.title
        holder.model.text = line.model
        holder.price.text = Money.format(line.total)
        holder.quantity.text = line.quantity.toString()

        holder.plus.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) onIncrease(adapterPosition)
        }
        holder.minus.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) onDecrease(adapterPosition)
        }
    }

    override fun getItemCount() = lines.size
}
