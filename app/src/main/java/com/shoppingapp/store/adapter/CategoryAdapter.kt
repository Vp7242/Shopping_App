package com.shoppingapp.store.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R
import com.shoppingapp.store.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var selected = 0

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.findViewById(R.id.categoryRoot)
        val icon: ImageView = itemView.findViewById(R.id.categoryIcon)
        val label: TextView = itemView.findViewById(R.id.categoryLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val category = categories[position]
        val active = position == selected

        holder.root.setBackgroundResource(
            if (active) R.drawable.bg_category_selected else R.drawable.bg_category_normal
        )
        holder.icon.setImageResource(category.iconRes)
        holder.icon.setColorFilter(
            if (active) Color.WHITE else Color.parseColor("#17181C"),
            PorterDuff.Mode.SRC_IN
        )
        holder.label.text = category.title
        holder.label.visibility = if (active) View.VISIBLE else View.GONE

        holder.root.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val previous = selected
            selected = adapterPosition
            notifyItemChanged(previous)
            notifyItemChanged(selected)
            onClick(categories[adapterPosition])
        }
    }

    override fun getItemCount() = categories.size
}
