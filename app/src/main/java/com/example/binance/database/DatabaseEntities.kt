package com.example.binance.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.binance.domainmodel.Currency

@Entity
data class DatabaseCurrency(
    @PrimaryKey
    val symbol: String,
    val priceChange: String,
    val priceChangePercent: String,
    val bidPrice: String,
    val askPrice: String
)

fun List<DatabaseCurrency>.asDomainModel(): List<Currency> {
    return map {
        Currency(
            symbol = it.symbol,
            priceChange = it.priceChange,
            priceChangePercent = it.priceChangePercent,
            bidPrice = it.bidPrice,
            askPrice = it.askPrice
        )
    }
}