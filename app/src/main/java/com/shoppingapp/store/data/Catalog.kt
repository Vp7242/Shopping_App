package com.shoppingapp.store.data

import com.shoppingapp.store.R
import com.shoppingapp.store.model.Category
import com.shoppingapp.store.model.Item

object Catalog {

    private val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip " +
            "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit " +
            "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt. Excepteur sint occaecat"

    private val cpuModels = listOf("Core i3", "Core i5", "Core i7")

    val banners = listOf(R.drawable.banner1, R.drawable.banner2)

    val categories = listOf(
        Category(0, "PC", R.drawable.cat1),
        Category(1, "Smartphone", R.drawable.cat2_phone),
        Category(2, "Headphone", R.drawable.cat3),
        Category(3, "Console", R.drawable.cat4),
        Category(4, "Camera", R.drawable.cat5),
        Category(5, "Smartwatch", R.drawable.cat6)
    )

    private val items = listOf(
        Item(
            0, 2, "Headphone 12A", 950.0, 4.6, true,
            listOf("Black", "Purple", "Pink", "Blue"),
            listOf(R.drawable.cat1_1), lorem
        ),
        Item(
            1, 0, "Business Laptop", 5500.0, 4.7, true, cpuModels,
            listOf(
                R.drawable.cat2_1, R.drawable.cat2_2, R.drawable.cat2_3,
                R.drawable.cat2_4, R.drawable.cat2_5
            ), lorem
        ),
        Item(2, 0, "Laptop A34f", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop3), lorem),
        Item(3, 0, "Laptop 128Gig", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop4), lorem),
        Item(4, 0, "Laptop B2003", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop2), lorem),
        Item(5, 0, "Business Laptop", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop5), lorem),
        Item(6, 0, "Laptop MOH345", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop6), lorem),
        Item(7, 0, "Laptop X1", 5500.0, 4.7, false, cpuModels, listOf(R.drawable.loptop1), lorem),
        Item(
            8, 4, "Digital Camera", 7500.0, 4.5, true,
            listOf("18-55mm", "35-200mm", "75-300mm"),
            listOf(R.drawable.cat3_1, R.drawable.cat3_2, R.drawable.cat3_3, R.drawable.cat3_4),
            lorem
        ),
        Item(
            9, 3, "PS5", 5000.0, 4.9, true,
            listOf("Slim", "Slim Digital"),
            listOf(R.drawable.cat4_1, R.drawable.cat4_2, R.drawable.cat4_3), lorem
        )
    )

    fun findCategory(id: Int): Category? = categories.firstOrNull { it.id == id }

    fun allItems(): List<Item> = items

    fun findItem(id: Int): Item? = items.firstOrNull { it.id == id }

    fun recommended(): List<Item> = items.filter { it.recommended }

    fun inCategory(categoryId: Int): List<Item> = items.filter { it.categoryId == categoryId }
}
