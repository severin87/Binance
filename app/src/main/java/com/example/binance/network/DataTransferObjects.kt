package com.example.binance.network

import com.example.binance.database.DatabaseCurrency

data class NetworkCurrency(
    val symbol: String,
    val priceChange: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String
)

fun List<NetworkCurrency>.asDatabaseModel(): Array<DatabaseCurrency> {
    return map {
        DatabaseCurrency(
            symbol = it.symbol,
            priceChange = it.priceChange,
            priceChangePercent = it.priceChangePercent,
            bidPrice = it.bidPrice,
            askPrice = it.askPrice
        )
    }.toTypedArray()
}