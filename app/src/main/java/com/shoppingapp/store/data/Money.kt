package com.shoppingapp.store.data

import java.util.Locale

object Money {

    fun format(amount: Double): String {
        return if (amount == Math.floor(amount)) {
            "₹" + amount.toLong()
        } else {
            String.format(Locale.US, "₹%.2f", amount)
        }
    }
}
