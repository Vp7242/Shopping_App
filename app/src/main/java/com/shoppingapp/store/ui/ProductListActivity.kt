package com.shoppingapp.store.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R
import com.shoppingapp.store.adapter.ProductAdapter
import com.shoppingapp.store.data.Catalog
import com.shoppingapp.store.data.WishStore
import java.util.Locale

class ProductListActivity : AppCompatActivity() {

    private var mode = MODE_ALL
    private var categoryId = 0
    private var query = ""
    private lateinit var adapter: ProductAdapter
    private lateinit var emptyState: View
    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        mode = intent.getIntExtra(EXTRA_MODE, MODE_ALL)
        categoryId = intent.getIntExtra(EXTRA_CATEGORY, 0)

        list = findViewById(R.id.productList)
        emptyState = findViewById(R.id.emptyState)
        adapter = ProductAdapter(emptyList()) { item ->
            startActivity(DetailActivity.forItem(this, item.id))
        }
        list.adapter = adapter

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<View>(R.id.checkOutButton).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        findViewById<TextView>(R.id.screenTitle).text = screenTitle()

        if (mode == MODE_ALL) {
            val search = findViewById<EditText>(R.id.searchField)
            search.visibility = View.VISIBLE
            search.doOnTextChanged { text, _, _, _ ->
                query = text.toString().trim().lowercase(Locale.getDefault())
                refresh()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun screenTitle(): String {
        return when (mode) {
            MODE_WISHLIST -> getString(R.string.nav_wishlist)
            MODE_CATEGORY -> (Catalog.findCategory(categoryId)?.title ?: "") + " category"
            else -> "All products"
        }
    }

    private fun refresh() {
        val source = when (mode) {
            MODE_WISHLIST -> WishStore(this).items()
            MODE_CATEGORY -> Catalog.inCategory(categoryId)
            else -> Catalog.allItems()
        }
        val shown = source.filter {
            query.isEmpty() || it.title.lowercase(Locale.getDefault()).contains(query)
        }

        adapter.submit(shown)
        emptyState.visibility = if (shown.isEmpty()) View.VISIBLE else View.GONE
        list.visibility = if (shown.isEmpty()) View.INVISIBLE else View.VISIBLE
    }

    companion object {
        private const val EXTRA_MODE = "mode"
        private const val EXTRA_CATEGORY = "category_id"

        private const val MODE_CATEGORY = 0
        private const val MODE_WISHLIST = 1
        private const val MODE_ALL = 2

        fun forCategory(context: Context, categoryId: Int): Intent =
            Intent(context, ProductListActivity::class.java)
                .putExtra(EXTRA_MODE, MODE_CATEGORY)
                .putExtra(EXTRA_CATEGORY, categoryId)

        fun forWishlist(context: Context): Intent =
            Intent(context, ProductListActivity::class.java).putExtra(EXTRA_MODE, MODE_WISHLIST)

        fun forAll(context: Context): Intent =
            Intent(context, ProductListActivity::class.java).putExtra(EXTRA_MODE, MODE_ALL)
    }
}
