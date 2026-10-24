package com.shoppingapp.store.model

data class Item(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val price: Double,
    val rating: Double,
    val recommended: Boolean,
    val models: List<String>,
    val images: List<Int>,
    val description: String
) {
    val cover: Int
        get() = images.first()
}
