package com.example.binance.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.binance.domainmodel.Currency

class DetailsViewModelFactory(private val currency: Currency) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(currency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
