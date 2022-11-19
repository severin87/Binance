package com.example.binance.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.binance.R
import com.example.binance.domainmodel.Currency
import java.math.RoundingMode

@BindingAdapter("priceChangePercentFormatted")
fun TextView.priceChangePercentFormatted(item: Currency) {
    val priceChangePercent =
        "${item.priceChangePercent.toBigDecimal().setScale(2, RoundingMode.UP)}"
    text = context.getString(R.string.price_change_percent_formatted, priceChangePercent)
}

@BindingAdapter("bidAskPriceFormatted")
fun TextView.bidAskPriceFormatted(item: Currency) {
    text = context.getString(R.string.bid_ask_price_formatted, item.bidPrice, item.askPrice)
}
