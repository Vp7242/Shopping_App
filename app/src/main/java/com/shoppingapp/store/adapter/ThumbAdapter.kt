package com.shoppingapp.store.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R

class ThumbAdapter(
    private val images: List<Int>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ThumbAdapter.Holder>() {

    private var selected = 0

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.findViewById(R.id.thumbRoot)
        val image: ImageView = itemView.findViewById(R.id.thumbImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_thumb, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.image.setImageResource(images[position])
        holder.root.setBackgroundResource(
            if (position == selected) R.drawable.bg_thumb_selected else R.drawable.bg_thumb_normal
        )
        holder.root.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val previous = selected
            selected = adapterPosition
            notifyItemChanged(previous)
            notifyItemChanged(selected)
            onClick(adapterPosition)
        }
    }

    override fun getItemCount() = images.size
}
