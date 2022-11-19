package com.example.binance.domainmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val symbol: String,
    val priceChange: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String
) : Parcelable