package com.shoppingapp.store.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shoppingapp.store.R
import com.shoppingapp.store.adapter.BannerAdapter
import com.shoppingapp.store.adapter.CategoryAdapter
import com.shoppingapp.store.adapter.ProductAdapter
import com.shoppingapp.store.data.Catalog

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var bannerPager: ViewPager2
    private lateinit var dotRow: LinearLayout

    private val bannerTick = object : Runnable {
        override fun run() {
            val count = Catalog.banners.size
            if (count > 1) {
                bannerPager.setCurrentItem((bannerPager.currentItem + 1) % count, true)
            }
            handler.postDelayed(this, BANNER_INTERVAL)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpHeader()
        setUpBanners()
        setUpCategories()
        setUpRecommended()
        setUpNavigation()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(bannerTick, BANNER_INTERVAL)
    }

    override fun onPause() {
        handler.removeCallbacks(bannerTick)
        super.onPause()
    }

    private fun setUpHeader() {
        val openAll = View.OnClickListener {
            startActivity(ProductListActivity.forAll(this))
        }
        findViewById<View>(R.id.searchButton).setOnClickListener(openAll)
        findViewById<View>(R.id.seeAllCategories).setOnClickListener(openAll)
        findViewById<View>(R.id.seeAllItems).setOnClickListener(openAll)
        findViewById<View>(R.id.bellButton).setOnClickListener {
            Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpBanners() {
        bannerPager = findViewById(R.id.bannerPager)
        dotRow = findViewById(R.id.dotRow)
        bannerPager.adapter = BannerAdapter(Catalog.banners)

        val size = (6 * resources.displayMetrics.density).toInt()
        repeat(Catalog.banners.size) {
            val dot = View(this)
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(size / 2, 0, size / 2, 0)
            dot.layoutParams = params
            dotRow.addView(dot)
        }
        paintDots(0)

        bannerPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                paintDots(position)
            }
        })
    }

    private fun paintDots(active: Int) {
        for (i in 0 until dotRow.childCount) {
            dotRow.getChildAt(i).setBackgroundResource(
                if (i == active) R.drawable.bg_dot_active else R.drawable.bg_dot_idle
            )
        }
    }

    private fun setUpCategories() {
        findViewById<RecyclerView>(R.id.categoryList).adapter =
            CategoryAdapter(Catalog.categories) { category ->
                startActivity(ProductListActivity.forCategory(this, category.id))
            }
    }

    private fun setUpRecommended() {
        findViewById<RecyclerView>(R.id.recommendList).adapter =
            ProductAdapter(Catalog.recommended()) { item ->
                startActivity(DetailActivity.forItem(this, item.id))
            }
    }

    private fun setUpNavigation() {
        findViewById<View>(R.id.navCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        findViewById<View>(R.id.navWishlist).setOnClickListener {
            startActivity(ProductListActivity.forWishlist(this))
        }
        findViewById<View>(R.id.navOrders).setOnClickListener { comingSoon() }
        findViewById<View>(R.id.navProfile).setOnClickListener { comingSoon() }
    }

    private fun comingSoon() {
        Toast.makeText(this, "This section is coming soon", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val BANNER_INTERVAL = 3500L
    }
}
