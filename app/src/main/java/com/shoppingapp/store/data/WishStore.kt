package com.shoppingapp.store.data

import android.content.Context
import com.shoppingapp.store.model.Item

class WishStore(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private fun readIds(): MutableSet<String> {
        return HashSet(prefs.getStringSet(KEY_IDS, emptySet()) ?: emptySet())
    }

    fun contains(itemId: Int): Boolean = readIds().contains(itemId.toString())

    fun toggle(itemId: Int): Boolean {
        val ids = readIds()
        val key = itemId.toString()
        val nowSaved = if (ids.contains(key)) {
            ids.remove(key)
            false
        } else {
            ids.add(key)
            true
        }
        prefs.edit().putStringSet(KEY_IDS, ids).apply()
        return nowSaved
    }

    fun items(): List<Item> {
        val ids = readIds()
        return Catalog.allItems().filter { ids.contains(it.id.toString()) }
    }

    companion object {
        private const val PREFS = "wish_store"
        private const val KEY_IDS = "ids"
    }
}
