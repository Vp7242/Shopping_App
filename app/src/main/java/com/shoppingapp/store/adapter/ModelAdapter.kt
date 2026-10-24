package com.shoppingapp.store.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R

class ModelAdapter(private val models: List<String>) :
    RecyclerView.Adapter<ModelAdapter.Holder>() {

    private var selected = 0

    class Holder(val chip: TextView) : RecyclerView.ViewHolder(chip)

    fun selectedModel(): String = models[selected]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false) as TextView
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val active = position == selected
        val chip = holder.chip
        chip.text = models[position]
        chip.setBackgroundResource(
            if (active) R.drawable.bg_chip_selected else R.drawable.bg_chip_normal
        )
        chip.setTextColor(
            ContextCompat.getColor(chip.context, if (active) R.color.blue else R.color.ink)
        )
        chip.setTypeface(null, if (active) Typeface.BOLD else Typeface.NORMAL)
        chip.setOnClickListener {
            val adapterPosition = holder.bindingAdapterPosition
            if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            val previous = selected
            selected = adapterPosition
            notifyItemChanged(previous)
            notifyItemChanged(selected)
        }
    }

    override fun getItemCount() = models.size
}
