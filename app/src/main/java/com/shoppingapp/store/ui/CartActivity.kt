package com.shoppingapp.store.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.shoppingapp.store.R
import com.shoppingapp.store.adapter.CartAdapter
import com.shoppingapp.store.data.CartStore
import com.shoppingapp.store.data.Money

class CartActivity : AppCompatActivity() {

    private lateinit var cart: CartStore
    private lateinit var adapter: CartAdapter
    private lateinit var list: RecyclerView
    private lateinit var emptyState: View
    private lateinit var subtotalValue: TextView
    private lateinit var deliveryValue: TextView
    private lateinit var taxValue: TextView
    private lateinit var totalValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cart = CartStore(this)

        list = findViewById(R.id.cartList)
        emptyState = findViewById(R.id.emptyState)
        subtotalValue = findViewById(R.id.subtotalValue)
        deliveryValue = findViewById(R.id.deliveryValue)
        taxValue = findViewById(R.id.taxValue)
        totalValue = findViewById(R.id.totalValue)

        adapter = CartAdapter(
            onIncrease = { position ->
                cart.increase(position)
                refresh()
            },
            onDecrease = { position ->
                cart.decrease(position)
                refresh()
            }
        )
        list.adapter = adapter

        findViewById<View>(R.id.backButton).setOnClickListener { finish() }
        findViewById<View>(R.id.checkOutButton).setOnClickListener { checkOut() }

        refresh()
    }

    private fun refresh() {
        val lines = cart.getLines()
        adapter.submit(lines)

        val empty = lines.isEmpty()
        emptyState.visibility = if (empty) View.VISIBLE else View.GONE
        list.visibility = if (empty) View.INVISIBLE else View.VISIBLE

        val subtotal = lines.sumOf { it.total }
        val delivery = if (empty) 0.0 else CartStore.DELIVERY_FEE
        val tax = subtotal * CartStore.TAX_RATE

        subtotalValue.text = Money.format(subtotal)
        deliveryValue.text = Money.format(delivery)
        taxValue.text = Money.format(tax)
        totalValue.text = Money.format(subtotal + delivery + tax)
    }

    private fun checkOut() {
        if (cart.getLines().isEmpty()) {
            Toast.makeText(this, R.string.empty_cart, Toast.LENGTH_SHORT).show()
            return
        }
        AlertDialog.Builder(this)
            .setTitle("Order placed")
            .setMessage("Thanks for shopping with us. Your order is on its way.")
            .setCancelable(false)
            .setPositiveButton("Done") { _, _ ->
                cart.clear()
                finish()
            }
            .show()
    }
}
