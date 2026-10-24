package com.shoppingapp.store.model

data class CartLine(
    val item: Item,
    val model: String,
    var quantity: Int
) {
    val total: Double
        get() = item.price * quantity
}
