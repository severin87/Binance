package com.example.binance.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.binance.domainmodel.Currency

class DetailsViewModel(currency: Currency) : ViewModel() {

    private val _currency = MutableLiveData<Currency>()
    val currency: LiveData<Currency>
        get() = _currency

    init {
        _currency.value = currency
    }
}
