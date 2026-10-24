package com.shoppingapp.store.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R
import com.shoppingapp.store.adapter.ModelAdapter
import com.shoppingapp.store.adapter.ThumbAdapter
import com.shoppingapp.store.data.CartStore
import com.shoppingapp.store.data.Catalog
import com.shoppingapp.store.data.Money
import com.shoppingapp.store.data.WishStore
import com.shoppingapp.store.model.Item

class DetailActivity : AppCompatActivity() {

    private lateinit var item: Item
    private lateinit var cart: CartStore
    private lateinit var wishes: WishStore
    private lateinit var modelAdapter: ModelAdapter
    private lateinit var favButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val found = Catalog.findItem(intent.getIntExtra(EXTRA_ITEM_ID, -1))
        if (found == null) {
            finish()
            return
        }
        item = found

        setContentView(R.layout.activity_detail)
        cart = CartStore(this)
        wishes = WishStore(this)

        val mainImage = findViewById<ImageView>(R.id.mainImage)
        mainImage.setImageResource(item.cover)

        findViewById<TextView>(R.id.detailTitle).text = item.title
        findViewById<TextView>(R.id.detailPrice).text = Money.format(item.price)
        findViewById<TextView>(R.id.detailRating).text = item.rating.toString()
        findViewById<TextView>(R.id.detailDescription).text = item.description

        val thumbs = findViewById<RecyclerView>(R.id.thumbList)
        if (item.images.size > 1) {
            thumbs.adapter = ThumbAdapter(item.images) { position ->
                mainImage.setImageResource(item.images[position])
            }
        } else {
            thumbs.visibility = View.GONE
        }

        modelAdapter = ModelAdapter(item.models)
        findViewById<RecyclerView>(R.id.modelList).adapter = modelAdapter

        favButton = findViewById(R.id.favButton)
        showFavouriteState()
        favButton.setOnClickListener {
            val saved = wishes.toggle(item.id)
            showFavouriteState()
            val message = if (saved) "Saved to your wishlist" else "Removed from your wishlist"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }

        findViewById<View>(R.id.addToCartButton).setOnClickListener {
            cart.add(item, modelAdapter.selectedModel())
            Toast.makeText(this, "Added to your cart", Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.buyNowButton).setOnClickListener {
            cart.add(item, modelAdapter.selectedModel())
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun showFavouriteState() {
        favButton.setImageResource(
            if (wishes.contains(item.id)) R.drawable.fav_icon_active else R.drawable.fav_icon
        )
    }

    companion object {
        private const val EXTRA_ITEM_ID = "item_id"

        fun forItem(context: Context, itemId: Int): Intent =
            Intent(context, DetailActivity::class.java).putExtra(EXTRA_ITEM_ID, itemId)
    }
}
