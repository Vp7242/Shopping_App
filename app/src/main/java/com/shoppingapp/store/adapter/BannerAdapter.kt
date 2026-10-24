package com.shoppingapp.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R

class BannerAdapter(private val banners: List<Int>) :
    RecyclerView.Adapter<BannerAdapter.Holder>() {

    class Holder(val image: ImageView) : RecyclerView.ViewHolder(image)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banner, parent, false) as ImageView
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.image.setImageResource(banners[position])
    }

    override fun getItemCount() = banners.size
}
