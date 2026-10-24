package com.shoppingapp.store.data

import android.content.Context
import com.shoppingapp.store.model.CartLine
import com.shoppingapp.store.model.Item
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CartStore(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getLines(): MutableList<CartLine> {
        val lines = mutableListOf<CartLine>()
        try {
            val array = JSONArray(prefs.getString(KEY_LINES, "[]"))
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val item = Catalog.findItem(obj.getInt("itemId")) ?: continue
                lines.add(CartLine(item, obj.getString("model"), obj.getInt("qty")))
            }
        } catch (e: JSONException) {
            return mutableListOf()
        }
        return lines
    }

    fun add(item: Item, model: String) {
        val lines = getLines()
        val existing = lines.firstOrNull { it.item.id == item.id && it.model == model }
        if (existing != null) {
            existing.quantity++
        } else {
            lines.add(CartLine(item, model, 1))
        }
        save(lines)
    }

    fun increase(position: Int) {
        val lines = getLines()
        if (position !in lines.indices) return
        lines[position].quantity++
        save(lines)
    }

    fun decrease(position: Int) {
        val lines = getLines()
        if (position !in lines.indices) return
        val line = lines[position]
        if (line.quantity <= 1) {
            lines.removeAt(position)
        } else {
            line.quantity--
        }
        save(lines)
    }

    fun clear() {
        prefs.edit().remove(KEY_LINES).apply()
    }

    private fun save(lines: List<CartLine>) {
        val array = JSONArray()
        try {
            for (line in lines) {
                val obj = JSONObject()
                obj.put("itemId", line.item.id)
                obj.put("model", line.model)
                obj.put("qty", line.quantity)
                array.put(obj)
            }
        } catch (e: JSONException) {
            return
        }
        prefs.edit().putString(KEY_LINES, array.toString()).apply()
    }

    companion object {
        const val DELIVERY_FEE = 100.0
        const val TAX_RATE = 0.05

        private const val PREFS = "cart_store"
        private const val KEY_LINES = "lines"
    }
}
