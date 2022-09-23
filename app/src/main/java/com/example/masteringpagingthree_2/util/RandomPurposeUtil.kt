package com.example.masteringpagingthree_2.util

import java.text.DecimalFormat

object RandomPurposeUtil {
    fun Number.formatCurrencyAmount(currencySymbol: String = "\u20A6") : String {
        val decimalFormatter = DecimalFormat("#,###.00")
        return "$currencySymbol ${decimalFormatter.format(this)}"
    }
}